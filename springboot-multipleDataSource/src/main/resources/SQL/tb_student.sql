/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.110.100
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.110.100:3306
 Source Schema         : multipleData3306

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 30/10/2019 21:15:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
INSERT INTO `tb_student` VALUES (1, 'xiahan', 23, '火星', '2019-10-30 21:14:59');

SET FOREIGN_KEY_CHECKS = 1;
