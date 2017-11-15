/*
Navicat MySQL Data Transfer

Source Server         : MySQL56
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : mybatisdb

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `ts` datetime DEFAULT NULL,
  `status` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lyne', '22', '2017-11-15 12:36:43', '1');
INSERT INTO `user` VALUES ('2', 'luffy', '25', '2017-11-29 12:36:47', '2');
