/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : rainapi

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 13/05/2023 20:53:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alipay_info
-- ----------------------------
DROP TABLE IF EXISTS `alipay_info`;
CREATE TABLE `alipay_info`  (
                                `orderNumber` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
                                `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
                                `totalAmount` float(10, 2) NOT NULL COMMENT '交易金额',
  `buyerPayAmount` float(10, 2) NOT NULL COMMENT '买家付款金额',
  `buyerId` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '买家在支付宝的唯一id',
  `tradeNo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付宝交易凭证号',
  `tradeStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易状态',
  `gmtPayment` datetime(0) NOT NULL COMMENT '买家付款时间',
  PRIMARY KEY (`orderNumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for api_order
-- ----------------------------
DROP TABLE IF EXISTS `api_order`;
CREATE TABLE `api_order`  (
                              `id` bigint(0) NOT NULL COMMENT '主键',
                              `interfaceId` bigint(0) NOT NULL COMMENT '接口id',
                              `userId` bigint(0) NOT NULL COMMENT '用户id',
                              `orderNumber` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
                              `total` bigint(0) NOT NULL COMMENT '购买数量',
                              `charging` float(255, 2) NOT NULL COMMENT '单价',
  `totalAmount` float(10, 2) NOT NULL COMMENT '交易金额',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '交易状态【0->待付款；1->已完成；2->无效订单】',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interface_charging
-- ----------------------------
DROP TABLE IF EXISTS `interface_charging`;
CREATE TABLE `interface_charging`  (
                                       `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `interfaceId` bigint(0) NOT NULL COMMENT '接口id',
                                       `charging` float(255, 2) NOT NULL COMMENT '计费规则（元/条）',
  `availablePieces` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口剩余可调用次数',
  `userId` bigint(0) NOT NULL COMMENT '创建人',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
                                   `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
                                   `methodName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SDK方法名',
                                   `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口地址',
                                   `requestHeader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求头',
                                   `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
                                   `status` int(0) NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
                                   `method` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求类型',
                                   `responseHeader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '响应头',
                                   `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
                                   `requestParams` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
                                   `userId` bigint(0) NOT NULL COMMENT '创建人',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '接口信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_lock
-- ----------------------------
DROP TABLE IF EXISTS `order_lock`;
CREATE TABLE `order_lock`  (
                               `id` bigint(0) NOT NULL COMMENT '主键',
                               `orderNumber` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
                               `chargingId` bigint(0) NOT NULL COMMENT '计费id',
                               `userId` bigint(0) NOT NULL COMMENT '用户id',
                               `lockNum` bigint(0) NOT NULL COMMENT '锁定数量',
                               `lockStatus` int(0) NOT NULL COMMENT '锁定状态(1-已锁定  0-已解锁 2-扣减)',
                               `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                               `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
                         `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
                         `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
                         `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别',
                         `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user / admin',
                         `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
                         `phoneNum` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
                         `accessKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'accessKey',
                         `secretKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'secretKey',
                         `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                         `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                         `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `uni_userAccount`(`userAccount`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_interface_info
-- ----------------------------
DROP TABLE IF EXISTS `user_interface_info`;
CREATE TABLE `user_interface_info`  (
                                        `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `userId` bigint(0) NOT NULL COMMENT '调用者 id',
                                        `interfaceInfoId` bigint(0) NOT NULL COMMENT '接口 id',
                                        `totalNum` int(0) NOT NULL DEFAULT 0 COMMENT '总调用次数',
                                        `leftNum` int(0) NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
                                        `status` int(0) NOT NULL DEFAULT 0 COMMENT '0-正常，1-禁用）',
                                        `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                        `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                        `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户调用接口关系' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for interface_audit
-- ----------------------------
-- auto-generated definition
create table interface_audit
(
    id          bigint auto_increment comment '主键'
        primary key,
    interfaceId bigint                             not null comment '接口ID',
    userId      bigint                             not null comment '申请人id',
    approverId  bigint                             null comment '审批人ID',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted   tinyint  default 0                 not null comment '逻辑删除标志',
    remark      varchar(225)                       null comment '备注',
    auditStatus int      default 0                 not null comment '0--待审核 1--审核结束'
);

use apidb;
-- 图表表
create table if not exists chart
(
    id           bigint auto_increment comment 'id' primary key,
    goal				 text  null comment '分析目标',
    `name`               varchar(128) null comment '图表名称',
    chartData    text  null comment '图表数据',
    chartType	   varchar(128) null comment '图表类型',
    genChart		 text	 null comment '生成的图表数据',
    genResult		 text	 null comment '生成的分析结论',
    status       varchar(128) not null default 'wait' comment 'wait,running,succeed,failed',
    execMessage  text   null comment '执行信息',
    userId       bigint null comment '创建用户 id',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '图表信息表' collate = utf8mb4_unicode_ci;



-- 个人模型表
create table if not exists individual_model
(
    id           bigint auto_increment comment 'id' primary key,
    goal				 text  null comment '分析目标',
    `name`               varchar(128) null comment '图表名称',
    chartData    text  null comment '图表数据',
    chartType	   varchar(128) null comment '图表类型',
    genChart		 text	 null comment '生成的图表数据',
    genResult		 text	 null comment '生成的分析结论',
    status       varchar(128) not null default 'wait' comment 'wait,running,succeed,failed',
    execMessage  text   null comment '执行信息',
    userId       bigint null comment '创建用户 id',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '图表信息表' collate = utf8mb4_unicode_ci;

-- ----------------------------+
-- Table structure for `popup_info`
-- ----------------------------
DROP TABLE IF EXISTS `popup_info`;
CREATE TABLE `popup_info` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `title` varchar(255) NOT NULL,
                              `content` text NOT NULL,
                              `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `popupLocation` varchar(255) DEFAULT NULL,
                              `isShow` int(11) NOT NULL DEFAULT '0',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of popup_info
-- ----------------------------
INSERT INTO `popup_info` VALUES ('11', '大黄蜂AI', '欢迎支持大黄蜂AI平台，可加入知识星球学习AI技术的开发技术', '2023-08-30 23:57:42', 'login', '1');
# INSERT INTO `popup_info` VALUES ('40', '大黄蜂AI第1版公告', '蓝猫AI目前已开源，源码地址：<a href=\"https://gitee.com/lixinjiuhao/chatgpt-web-java\" style=\"color: #58a6ff\">点击联系我们</a>', '2023-05-30 23:55:16', 'index', '1');




-- ----------------------------
-- Table structure for `prompt_model`
-- ----------------------------
DROP TABLE IF EXISTS `prompt_model`;
CREATE TABLE `prompt_model` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `type` varchar(100) NOT NULL COMMENT '分类',
                                `title` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '标题',
                                `introduce` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '简介',
                                `demo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '示例',
                                `content` varchar(1000) CHARACTER SET utf8mb4 NOT NULL COMMENT '提示内容',
                                `state` tinyint(4) NOT NULL COMMENT '0，无效；1，有效',
                                `sort` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序值',
                                `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Records of prompt_model
-- ----------------------------
INSERT INTO `prompt_model` VALUES ('1', 'Java', 'Java代码优化', '只需要将你想要的优化的代码复制到输入框，AI将会自动帮你优化它', '请输入/粘贴你想要优化的代码', '假如你是高级Java开发工程师，有着丰富的代码编写能力，那么请使用JDK8新语法将下面的代码进行优化并且将优化后的代码能够被markdowm渲染的代码出来：', '1', '0', '2023-05-21 22:42:46', '2023-05-21 22:56:34');
INSERT INTO `prompt_model` VALUES ('7', 'Java', '使用JDK8新特性优化代码', '只需要将你想要的优化的代码复制到输入框，AI将会自动使用JDK8新语法帮你优化它', '请输入/粘贴你想要优化的代码', '假如你是高级Java开发工程师，非常擅长使用JDK8新语法，那么请使用JDK8新语法将下面的代码进行优化并且将优化后的代码能够被markdowm渲染的代码出来：', '1', '0', '2023-05-21 22:47:14', '2023-05-21 22:55:46');
INSERT INTO `prompt_model` VALUES ('8', 'Java', 'Java面试打分工具', '只需要将你的问题和回答复制到输入框，AI将会自动根据你的问题和回答进行分析和打分', '问题：在synchronized代码块中调用wait方法进入等待的线程和因为拿不到锁而等待线程是否同一种状态？blocking？waiting？\r\n回答：1. 因为拿不到锁会处于 blocking状态；\r\n2. 调用wait方法，会处于 waiting状态，不会释放锁。\r\n', '假如你是Java面试官，有着非常丰富的面试经验，那么请基于下面的问题和回答进行系统的分析，给出相应的分数，并且以markdown的格式分点输出回答中不足的地方并且加以补充：', '1', '0', '2023-05-21 22:58:02', '2023-05-21 23:15:45');





-- ----------------------------
-- Table structure for `user_access_rule`
-- ----------------------------
DROP TABLE IF EXISTS `user_access_rule`;
CREATE TABLE `user_access_rule` (
                                    `id` bigint(22) NOT NULL COMMENT '用户id',
                                    `user_id` bigint(22) NOT NULL,
                                    `service_type` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '模型标识',
                                    `use_number` tinyint(4) NOT NULL DEFAULT '10' COMMENT '使用次数, 如果 = -2 代表不限制次数',
                                    `start_effective_time` datetime NOT NULL COMMENT '开始生效时间',
                                    `end_effective_time` datetime NOT NULL COMMENT '有效结束时间',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                    `update_user` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户访问规则';


-- ----------------------------
-- Records of user_access_rule
-- ----------------------------
INSERT INTO `user_access_rule` VALUES ('1664427904947597313', '1664427904784019457', 'chat_gpt_model3.5', '30', '2023-06-02 08:25:11', '2023-06-03 08:25:11', '2023-06-02 08:25:11', null, null);




-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
                             `id` bigint(22) NOT NULL,
                             `username` varchar(64) DEFAULT '小可爱！' COMMENT '用户昵称',
                             `open_id` varchar(128) DEFAULT NULL COMMENT '微信授权的openId',
                             `avatar` varchar(128) DEFAULT '/src/assets/avatar.jpg' COMMENT '用户头像',
                             `phone` varchar(11) DEFAULT NULL COMMENT '电话信息',
                             `account` varchar(32) NOT NULL COMMENT '用户账号',
                             `user_level` varchar(16) DEFAULT 'common_user' COMMENT '用户等级',
                             `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '账号状态，0正常，1禁用',
                             `password` varchar(64) NOT NULL COMMENT '登录密码',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `account_index` (`account`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1664427904784019457', '小可爱！', null, '/src/assets/avatar.jpg', null, '18230675983', 'common_user', '0', 'acaaf694427dcb42423626d4aa74c4ae', '2023-06-02 08:25:11', null);




