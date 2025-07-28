# 校园管理系统 API 文档

**基础URL**: `http://10.168.89.204:8080`

**认证方式**: Bearer Token (JWT)

## 认证API

### 1. 账号密码登录

**URL**: `/api/login`

**方法**: `POST`

**请求体**:
```json
{
  "loginType": "account",
  "username": "string",
  "password": "string"
}
```

**成功响应**:
```json
{
  "success": true,
  "token": "string",
  "user": {
    "id": 123,
    "name": "张三",
    "email": "zhangsan@example.com",
    "role": "student"
  }
}
```

### 2. 邮箱密码登录

**URL**: `/api/login`

**方法**: `POST`

**请求体**:
```json
{
  "loginType": "email",
  "email": "string",
  "password": "string"
}
```

**响应**: 同账号密码登录

### 3. 邮箱验证码登录

**URL**: `/api/login/email-code`

**方法**: `POST`

**请求体**:
```json
{
  "loginType": "email_code",
  "email": "string",
  "code": "string"
}
```

**响应**: 同账号密码登录

### 4. 发送验证码

**URL**: `/api/login/send-code`

**方法**: `POST`

**请求体**:
```json
{
  "email": "string"
}
```

**成功响应**:
```json
{
  "success": true,
  "message": "验证码已发送"
}
```

### 5. 验证Token有效性

**URL**: `/api/auth/verify`

**方法**: `GET`

**请求头**:
```
Authorization: Bearer <token>
```

**成功响应**:
```json
{
  "success": true,
  "user": {
    "id": 123,
    "name": "张三",
    "email": "zhangsan@example.com",
    "role": "student"
  }
}
```

## 错误代码

| 错误代码 | 描述 |
|---------|------|
| 1001 | 无效的凭证 |
| 1002 | 无效的验证码 |
| 1003 | 账号被锁定 |
| 1004 | 无访问权限 |
| 1005 | 资源不存在 |
| 2001 | 参数验证失败 |
| 9001 | 服务器内部错误 |

## 使用示例

### JavaScript 示例
```javascript
// 登录示例
const response = await fetch('http://10.168.89.204:8080/api/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    loginType: 'account',
    username: 'testuser',
    password: 'password123'
  })
});
const data = await response.json();

// 获取学生信息示例
const studentResponse = await fetch('http://10.168.89.204:8080/api/students/123', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${data.token}`
  }
});
```

### cURL 示例
```bash
# 登录
curl -X POST http://10.168.89.204:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"loginType":"account","username":"testuser","password":"password123"}'

# 获取学生信息
curl -X GET http://10.168.89.204:8080/api/students/123 \
  -H "Authorization: Bearer <token>"
```
