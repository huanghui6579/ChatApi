/*
Navicat MySQL Data Transfer

Source Server         : ibaixin
Source Server Version : 50173
Source Host           : www.ibaixin.net:3306
Source Database       : ibaixin_chat_api

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-09-06 14:46:28
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
  `username` varchar(32) NOT NULL COMMENT '用户名，主键',
  `nickName` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `realName` varchar(255) DEFAULT NULL COMMENT '用户真实姓名',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `street` varchar(255) DEFAULT NULL COMMENT '具体的街道地址',
  `mobilePhone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(255) DEFAULT NULL COMMENT '固定电话',
  `avatarPath` varchar(255) DEFAULT NULL COMMENT '头像路径，目前就是文件名称',
  `mimeType` varchar(255) DEFAULT NULL COMMENT '文件的mime类型',
  `gender` tinyint(4) DEFAULT '0' COMMENT '性别，默认是0：未知，1：男，2:女',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `hash` varchar(255) DEFAULT NULL COMMENT '文件的MD5 hash值',
  `countryId` int(11) DEFAULT NULL COMMENT '国家编码，如：中国---86',
  `provinceId` int(11) DEFAULT NULL COMMENT '省份的编码',
  `cityId` int(11) DEFAULT NULL COMMENT '城市的编码',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
