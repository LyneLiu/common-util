/*
Navicat MySQL Data Transfer

Source Server         : MySQL56
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : springbootdb

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2017-07-28 17:59:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_sex` varchar(32) DEFAULT NULL,
  `nick_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('2', 'conanli', '123456', null, null);
INSERT INTO `users` VALUES ('3', 'conanli', '123456', null, null);
INSERT INTO `users` VALUES ('28', 'aa', 'a123456', 'MAN', null);
INSERT INTO `users` VALUES ('29', 'bb', 'b123456', 'WOMAN', null);
INSERT INTO `users` VALUES ('30', 'cc', 'b123456', 'WOMAN', 'neo');
INSERT INTO `users` VALUES ('31', 'aa', 'a123456', 'MAN', null);
INSERT INTO `users` VALUES ('32', 'bb', 'b123456', 'WOMAN', null);
INSERT INTO `users` VALUES ('33', 'cc', 'b123456', 'WOMAN', null);
