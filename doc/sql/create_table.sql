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
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户表';