/*
Navicat MySQL Data Transfer

Source Server         : MySQL56
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : springbootdb

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2017-07-28 18:00:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `publisher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_publisher` (`publisher_id`),
  KEY `FK_author` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('2', '哈哈哈', '9781-1234-1111', 'another_test_to_change_title', '1', '1');
INSERT INTO `book` VALUES ('3', '这是一本奇怪的书', '9781-1234-5678', '你愁啥', '3', '1');
