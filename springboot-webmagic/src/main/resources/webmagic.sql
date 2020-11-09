/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.110.100-3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.110.100:3306
 Source Schema         : webmagic

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 09/11/2020 20:39:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for manhua_url
-- ----------------------------
DROP TABLE IF EXISTS `manhua_url`;
CREATE TABLE `manhua_url`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `del` tinyint(1) DEFAULT NULL,
  `creat_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `img_url`(`img_url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7942 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msg_log
-- ----------------------------
DROP TABLE IF EXISTS `msg_log`;
CREATE TABLE `msg_log`  (
  `msg_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '消息唯一标识',
  `msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '消息体, json格式化',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '交换机',
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由键',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态: 0投递中 1投递成功 2投递失败 3已消费',
  `try_count` int(11) NOT NULL DEFAULT 0 COMMENT '重试次数',
  `next_try_time` datetime(0) DEFAULT NULL COMMENT '下一次重试时间',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_id`) USING BTREE,
  UNIQUE INDEX `unq_msg_id`(`msg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息投递日志' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
