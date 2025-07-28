-- 为games表添加tags字段
ALTER TABLE games ADD COLUMN tags TEXT COMMENT '游戏标签，逗号分隔';

-- 删除不再需要的game_tags表
DROP TABLE IF EXISTS game_tags;
