### API 文档

---

#### **认证模块 (auth.js)**
##### 1. 学号密码登录
- **请求方式**: POST
- **端点**: `/auth/login/password`
- **请求参数**:
  ```json
  {
    "msg": "登录学号密码",
    "user_number": "3122230220",
    "passwd": "Huawei@123"
  }
  ```
- **成功响应示例**:
  ```json
  {
    "code": 200,
    "data": {
      "user_id": 1001,
      "user_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
      "user_name": "张三",
      "user_email": "zhangsan@example.com",
      "role": "学生"
    }
  }
  ```
- **SQL 示例**:
  ```sql
  SELECT user_id, password_hash, real_name AS user_name, email AS user_email, role 
  FROM users 
  WHERE student_id = '3122230220';
  ```
- **控制器逻辑**:
  ```javascript
  async function loginWithPassword(req, res) {
    const { user_number, passwd } = req.body;
    const user = await db.query('SELECT * FROM users WHERE student_id = ?', [user_number]);
    
    if (!user) return res.status(404).json({ msg: "用户不存在" });
    if (!bcrypt.compare(passwd, user.password_hash)) {
      return res.status(401).json({ msg: "密码错误" });
    }
    
    const token = jwt.sign({ id: user.user_id }, SECRET_KEY, { expiresIn: '8h' });
    res.json({
      user_id: user.user_id,
      user_token: token,
      user_name: user.real_name,
      user_email: user.email,
      role: user.role
    });
  }
  ```

---

#### **游戏模块 (game.js)**
##### 1. 获取用户游戏列表
- **请求方式**: GET
- **端点**: `/games/user/:userId`
- **成功响应示例**:
  ```json
  [
    {
      "id": 501,
      "title": "太空冒险",
      "status": "私有",
      "views": 120,
      "created_at": "2023-05-20"
    }
  ]
  ```
- **SQL 示例**:
  ```sql
  SELECT id, title, is_public AS status, views, created_at 
  FROM games 
  WHERE user_id = 1001;
  ```

##### 2. 创建新游戏
- **请求方式**: POST
- **端点**: `/games`
- **请求参数**:
  ```json
  {
    "title": "迷宫探险",
    "url": "https://game.example.com",
    "description": "益智解谜游戏",
    "tags": "益智,冒险",
    "is_public": false
  }
  ```
- **SQL 示例**:
  ```sql
  INSERT INTO games (title, url, description, tags, user_id, is_public)
  VALUES ('迷宫探险', 'https://game.example.com', '益智解谜游戏', '益智,冒险', 1001, 0);
  ```

##### 3. 审核游戏
- **请求方式**: POST
- **端点**: `/games/:gameId/review`
- **请求参数**:
  ```json
  {
    "action": "approve",
    "reason": "符合规范"
  }
  ```
- **SQL 示例**:
  ```sql
  UPDATE games 
  SET status = 'approved', reviewed_at = NOW(), reviewer_id = 2001 
  WHERE id = 501;
  ```

---

#### **日志模块 (logs.js)**
##### 1. 获取日志
- **请求方式**: GET
- **端点**: `/logs`
- **查询参数**:
  ```json
  {
    "start_date": "2023-10-01",
    "end_date": "2023-10-31",
    "user_id": 1001,
    "action_type": "login"
  }
  ```
- **成功响应示例**:
  ```json
  [
    {
      "id": 701,
      "action": "login",
      "user_id": 1001,
      "timestamp": "2023-10-05 14:30:22",
      "ip": "192.168.1.1"
    }
  ]
  ```
- **SQL 示例**:
  ```sql
  SELECT id, action, user_id, created_at AS timestamp, ip 
  FROM logs 
  WHERE created_at BETWEEN '2023-10-01' AND '2023-10-31'
    AND user_id = 1001
    AND action = 'login';
  ```

---

#### **用户模块 (user.js)**
##### 1. 获取用户列表
- **请求方式**: GET
- **端点**: `/users`
- **成功响应示例**:
  ```json
  [
    {
      "id": 1001,
      "username": "zhangsan",
      "real_name": "张三",
      "role": "学生",
      "is_active": true
    }
  ]
  ```
- **SQL 示例**:
  ```sql
  SELECT id, username, real_name, role, is_active 
  FROM users;
  ```

##### 2. 更改用户状态
- **请求方式**: PATCH
- **端点**: `/users/:userId/status`
- **请求参数**:
  ```json
  { "status": false }
  ```
- **SQL 示例**:
  ```sql
  UPDATE users 
  SET is_active = 0 
  WHERE id = 1001;
  ```

---

### 关键控制器逻辑
#### 游戏审核控制器
```javascript
async function reviewGame(req, res) {
  const { gameId } = req.params;
  const { action, reason } = req.body;
  
  const validActions = ['approve', 'reject'];
  if (!validActions.includes(action)) {
    return res.status(400).json({ error: "无效操作类型" });
  }

  const game = await db.query('SELECT * FROM games WHERE id = ?', [gameId]);
  if (!game) return res.status(404).json({ error: "游戏不存在" });

  // 更新状态
  const newStatus = action === 'approve' ? 'approved' : 'rejected';
  await db.query(
    `UPDATE games SET status = ?, review_reason = ? WHERE id = ?`,
    [newStatus, reason, gameId]
  );

  // 记录审核日志
  await db.query(
    `INSERT INTO audit_logs (game_id, action, reviewer_id) VALUES (?, ?, ?)`,
    [gameId, action, req.user.id]
  );

  res.json({ message: `游戏已${action === 'approve' ? '通过' : '拒绝'}` });
}
```

#### 用户状态控制器
```javascript
async function changeUserStatus(req, res) {
  const { userId } = req.params;
  const { status } = req.body;
  
  const user = await db.query('SELECT * FROM users WHERE id = ?', [userId]);
  if (!user) return res.status(404).json({ error: "用户不存在" });

  await db.query('UPDATE users SET is_active = ? WHERE id = ?', [status, userId]);
  
  // 记录操作日志
  await db.query(
    `INSERT INTO user_logs (action, target_user_id, executor_id) 
     VALUES ('status_change', ?, ?)`,
    [userId, req.user.id]
  );

  res.json({ message: `用户状态已${status ? '启用' : '禁用'}` });
}
```

---

### 数据库表结构示例
#### `users` 表
| 字段          | 类型         | 说明               |
|---------------|--------------|--------------------|
| id            | INT          | 用户ID (主键)      |
| student_id    | VARCHAR(20)  | 学号               |
| password_hash | VARCHAR(100) | 加密密码           |
| real_name     | VARCHAR(50)  | 真实姓名           |
| role          | ENUM         | 角色(学生/教师/管理员)|
| is_active     | BOOLEAN      | 账户状态           |

#### `games` 表
| 字段         | 类型          | 说明                     |
|--------------|---------------|--------------------------|
| id           | INT           | 游戏ID (主键)            |
| title        | VARCHAR(100)  | 游戏名称                 |
| url          | VARCHAR(200)  | 游戏访问链接             |
| status       | ENUM          | 状态(审核中/已发布/拒绝) |
| reviewer_id  | INT           | 审核人ID                 |
| user_id      | INT           | 创建用户ID               |

> 完整实现需结合具体业务逻辑调整SQL和控制器代码。
