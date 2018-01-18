DROP TABLE IF EXISTS user_info;

# user_info 用户信息
CREATE TABLE user_info (
  user_id     BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  account     VARCHAR(10) NOT NULL,
  password    VARCHAR(64) NOT NULL,
  create_time DATETIME(3) NOT NULL,
  last_time   DATETIME(3) NOT NULL

)
  ENGINE = INNODB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS user_token;
# user_token Token表
CREATE TABLE user_token (
  user_id     BIGINT UNSIGNED,
  token       VARCHAR(32) NOT NULL,
  status      TINYINT DEFAULT 0,
  create_time DATETIME(3) NOT NULL,
  last_time   DATETIME(3) NOT NULL

)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS file_info;
# file_info 文件信息表
CREATE TABLE file_info (
  file_id     BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id     BIGINT UNSIGNED,
  md5         VARCHAR(32)  NOT NULL,
  path        VARCHAR(100) NOT NULL,
  name        VARCHAR(60)  NOT NULL,
  size        INT UNSIGNED    DEFAULT 0,
  status      TINYINT         DEFAULT 0,
  create_time DATETIME(3)  NOT NULL,
  last_time   DATETIME(3)  NOT NULL
)
  ENGINE = INNODB
  AUTO_INCREMENT = 101
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS share_info;
# share_info 分享信息表
CREATE TABLE share_info (
  share_id    BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id     BIGINT UNSIGNED,
  file_id     BIGINT UNSIGNED,
  password    VARCHAR(10),
  shortlink   VARCHAR(32),
  status      TINYINT         DEFAULT 0,
  create_time DATETIME(3) NOT NULL,
  last_time   DATETIME(3) NOT NULL
)
  ENGINE = INNODB
  AUTO_INCREMENT = 1000001
  DEFAULT CHARSET = utf8;
