# JU-CONNECT

## 项目背景

1. 前端开发需要调用后台的接口
2. 使用现成的系统功能，就比如：http://api.btstu.cn/

==> **做一个API接口平台**

- 考虑安全性，防止攻击
- 不能随意调用（限流、开通服务）
- 统计调用次数
- 计费
- 流量保护
- API接口接入
- ……

## 项目介绍

一个提供API接口调用的平台，用户可以注册登录、开通接口调用权限。用户可以使用接口，并且每次调用会进行统计。管理员可以发布接口、下线接口、接入接口，以及可视化接口的调用情况、数据。

## 项目架构

![架构图](assets/架构图.png)

## 技术选型

### 后端

- JDK11
- SpringBoot 2.7.x
- MySQL
- MyBatis && MyBatis Plus
- Dubbo
- Nacos
- Spring Cloud Gateway

### 前端

- Vite
- Ant Design Vue
- Ant Design Vue Components
- Axios

## 数据库设计

### 用户表

| 字段 | 类型 | 说明 |
|----|----|----|
|    |    |    |

建表语句：

```sql

```

### 接口表

| 字段         | 类型       | 说明                                         |
|------------|----------|--------------------------------------------|
| id         | bigint   | 接口id，主键                                    |
| apiName    | varchar  | 接口名称                                       |
| apiUrl     | varchar  | 接口地址                                       |
| reqParam   | varchar  | 接口参数（json格式）                               |
| reqMethod  | tinyint  | 接口请求类型（0-Get, 1-Post, 2-Put, 3-Delete, ……） |
| reqHeader  | text     | 请求头（json格式）                                |
| respHeader | text     | 响应头（json格式）                                |
| apiStatus  | tinyint  | 接口状态（0 - 下线， 2 - 上线）                       |
| userId     | bigint   | 接口创建人id                                    |
| createTime | datetime | 创建时间                                       |
| updateTime | datetime | 更新时间                                       |
| isDelete   | tinyint  | 逻辑删除标志（0-未删除，1-已删除）                        |

建表语句：

```sql
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
```

reqParam字段的JSON格式：

```json
[
  {
    "type": "String",
    "arg": "username"
  }
]
```

MOCK DATA：

```sql
INSERT INTO `interface_info` (apiName, apiUrl, reqParam, reqMethod, reqHeader, respHeader, userId)
VALUES ('User Registration', 'https://api.example.com/register', '[{"type": "String", "arg": "username"}]', 1,
        '{"Content-Type": "application/json", "Authorization": "Bearer token123"}',
        '{"Content-Type": "application/json"}', 1),
       ('User Login', 'https://api.example.com/login', '[{"type": "String", "arg": "username"}]', 1,
        '{"Content-Type": "application/json"}',
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}', 1),
       ('Get User Profile', 'https://api.example.com/user/profile', null, 0,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Update User Profile', 'https://api.example.com/user/profile', '[{"type": "String", "arg": "username"}]', 2,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Delete User Account', 'https://api.example.com/user/account', null, 3,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Create Product', 'https://api.example.com/product', '[{"type": "String", "arg": "username"}]', 1,
        '{"Content-Type": "application/json", "Authorization": "Bearer token123"}',
        '{"Content-Type": "application/json"}', 1),
       ('Get Product Details', 'https://api.example.com/product/123', null, 0,
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}',
        '{"Content-Type": "application/json"}', 1),
       ('Update Product Details', 'https://api.example.com/product/123', '[{"type": "String", "arg": "username"}]', 2,
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}',
        '{"Content-Type": "application/json"}', 1),
       ('Delete Product', 'https://api.example.com/product/123', null, 3,
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}',
        '{"Content-Type": "application/json"}', 1),
       ('List Orders', 'https://api.example.com/orders', null, 0,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Create Order', 'https://api.example.com/orders', '[{"type": "String", "arg": "username"}]', 1,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Retrieve Order', 'https://api.example.com/orders/123', null, 0,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Cancel Order', 'https://api.example.com/orders/123/cancel', null, 3,
        '{"Content-Type": "application/json", "Authorization": "Bearer token789"}',
        '{"Content-Type": "application/json"}', 1),
       ('Create Payment', 'https://api.example.com/payments', '[{"type": "String", "arg": "username"}]', 1,
        '{"Content-Type": "application/json", "Authorization": "Bearer token123"}',
        '{"Content-Type": "application/json"}', 1),
       ('Get Payment Details', 'https://api.example.com/payments/456', null, 0,
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}',
        '{"Content-Type": "application/json"}', 1),
       ('Update Payment Details', 'https://api.example.com/payments/456', '[{"type": "String", "arg": "username"}]', 2,
        '{"Content-Type": "application/json", "Authorization": "Bearer token456"}',
        '{"Content-Type": "application/json"}', 1);
```

