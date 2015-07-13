/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50539
Source Host           : localhost:3306
Source Database       : chat_api

Target Server Type    : MYSQL
Target Server Version : 50539
File Encoding         : 65001

Date: 2015-07-13 21:49:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
  `id` varchar(36) NOT NULL COMMENT '文件的唯一标识',
  `sender` varchar(255) NOT NULL COMMENT '文件的发送者',
  `receiver` varchar(255) DEFAULT NULL COMMENT '文件的接收者',
  `fileName` varchar(255) NOT NULL COMMENT '原始文件的名称',
  `creationDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文件的创建时间',
  `hasThumb` tinyint(4) DEFAULT '0' COMMENT '文件是否有缩略图，只有图片才有缩略图，0表示没有，1表示有',
  `mimeType` varchar(255) DEFAULT NULL COMMENT '文件的mime类型',
  `sotreName` varchar(255) DEFAULT NULL COMMENT '文件的存储到本地的名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_vcard
-- ----------------------------
DROP TABLE IF EXISTS `t_vcard`;
CREATE TABLE `t_vcard` (
  `username` varchar(32) NOT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `avatarPath` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT '0',
  `signature` varchar(255) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
