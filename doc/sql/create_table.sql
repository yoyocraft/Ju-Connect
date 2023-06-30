-- 创建库
create database if not exists ju_connect;

-- 切换库
use ju_connect;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    nickname     varchar(32)                        null comment '用户昵称',
    userAccount  varchar(16)                        not null comment '账号',
    userPassword varchar(32)                        not null comment '密码',
    salt         varchar(8)                         not null comment '盐值',
    userAvatar   varchar(256)                       null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userRole     tinyint  default 0                 not null comment '用户角色：0 - user / 1 - admin',
    accessKey    varchar(64)                        null comment 'accessKey',
    secretKey    varchar(64)                        null comment 'secretKey',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户表';

CREATE TABLE interface_info
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '接口id，主键',
    apiName    VARCHAR(255) NOT NULL COMMENT '接口名称',
    apiUrl     VARCHAR(255) NOT NULL COMMENT '接口地址',
    reqParam   VARCHAR(255) NULL COMMENT '接口参数（json格式）',
    reqMethod  TINYINT      NOT NULL COMMENT '接口请求类型（0-Get, 1-Post, 2-Put, 3-Delete, ……）',
    reqHeader  TEXT         NULL COMMENT '请求头（json格式）',
    respHeader TEXT         NULL COMMENT '响应头（json格式）',
    apiStatus  TINYINT      NOT NULL DEFAULT 0 COMMENT '接口状态（0 - 下线， 1 - 上线）',
    userId     BIGINT       NOT NULL COMMENT '接口创建人id',
    createTime DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDelete   TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除标志（0-未删除，1-已删除）',
    index idx_userId (userId)
) COMMENT '接口信息表';