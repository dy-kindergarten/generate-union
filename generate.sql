/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.5.62-0ubuntu0.14.04.1 : Database - iptv_hd
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iptv_hd` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `iptv_hd`;

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(500) DEFAULT NULL COMMENT '活动专题真实连接',
  `cname` varchar(100) DEFAULT NULL COMMENT '活动专题中文名称',
  `pic` varchar(100) DEFAULT NULL COMMENT '推荐位图片',
  `ctype` int(11) DEFAULT NULL COMMENT '类型(1.最新2.儿童3.明星4.节日)',
  `csort` int(11) DEFAULT NULL COMMENT '在活动专题页的排序,越大越靠前',
  `socnew` varchar(100) DEFAULT NULL COMMENT '配置在入口的关键字',
  `pos` int(11) DEFAULT NULL COMMENT '如果需要返回首页,选中的位置编号',
  `cline` int(11) DEFAULT NULL COMMENT '是否在入口配置该活动专题快速通道',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `activ` (`cline`,`csort`,`ctype`,`socnew`,`url`(255)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `app_data` */

DROP TABLE IF EXISTS `app_data`;

CREATE TABLE `app_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `zone` varchar(100) DEFAULT NULL COMMENT '数据区',
  `ckey` varchar(100) DEFAULT NULL COMMENT '数据键',
  `cval` varchar(500) DEFAULT NULL COMMENT '任意字符串',
  `dtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appd` (`ckey`,`zone`)
) ENGINE=InnoDB AUTO_INCREMENT=16540 DEFAULT CHARSET=utf8;

/*Table structure for table `artist` */

DROP TABLE IF EXISTS `artist`;

CREATE TABLE `artist` (
  `id` int(11) NOT NULL COMMENT '艺人标识ID',
  `cname` varchar(100) DEFAULT NULL COMMENT '姓名',
  `cname_len` int(11) DEFAULT NULL COMMENT '姓名长度',
  `abbr` varchar(50) DEFAULT NULL COMMENT '姓名拼音缩写',
  `pic` varchar(200) DEFAULT NULL COMMENT '头像',
  `ctype` int(11) DEFAULT NULL COMMENT '艺人性别或者类型(合唱，对唱，组合)',
  `csort` int(11) DEFAULT NULL COMMENT '排序',
  `cdes` varchar(250) DEFAULT NULL COMMENT '描述',
  `dtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `cart` (`id`,`csort`,`abbr`,`ctype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `calendar` */

DROP TABLE IF EXISTS `calendar`;

CREATE TABLE `calendar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctype` int(11) DEFAULT NULL,
  `cdes` text COMMENT '组描述,一段用于显示描述组作用的文字',
  `pic` varchar(20) DEFAULT NULL,
  `dtime` datetime DEFAULT NULL COMMENT '行为发生时间',
  PRIMARY KEY (`id`),
  KEY `cale` (`id`,`ctype`,`dtime`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Table structure for table `cgroup` */

DROP TABLE IF EXISTS `cgroup`;

CREATE TABLE `cgroup` (
  `id` int(11) NOT NULL COMMENT '标识ID',
  `cname` varchar(200) DEFAULT NULL COMMENT '组名称(例如:怀旧之声,儿童歌曲)',
  `cdes` text COMMENT '组描述,一段用于显示描述组作用的文字',
  `ctype` varchar(50) DEFAULT NULL COMMENT '组类型：歌单，专题，控序，组合包等',
  `pic` varchar(200) DEFAULT NULL COMMENT '相关图片：如专题背景',
  PRIMARY KEY (`id`),
  KEY `cgr` (`id`,`ctype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cgroup_item` */

DROP TABLE IF EXISTS `cgroup_item`;

CREATE TABLE `cgroup_item` (
  `id_cgroup` int(11) DEFAULT NULL COMMENT '组id(指向cgroup表)',
  `id_item` int(11) DEFAULT NULL COMMENT '歌曲id(指向song表)',
  `cnode` varchar(50) DEFAULT NULL COMMENT '精准控序，主要用于专题控序，检索控序等',
  `csort` varchar(50) DEFAULT '0',
  `ctype` int(11) DEFAULT NULL,
  KEY `cgri` (`id_cgroup`,`id_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `zone` varchar(100) NOT NULL DEFAULT '' COMMENT '全局：数据区名称',
  `ckey` varchar(100) NOT NULL COMMENT '仅仅作为一个键,用于存放cval字段信息',
  `cval` varchar(200) DEFAULT NULL COMMENT '用于存储任何复用性很强的组件/配置的内容',
  `desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ckey`),
  KEY `cf` (`zone`,`ckey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `daily_flow` */

DROP TABLE IF EXISTS `daily_flow`;

CREATE TABLE `daily_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hw` varchar(500) DEFAULT NULL,
  `zte` varchar(500) DEFAULT NULL,
  `allof` varchar(500) DEFAULT NULL,
  `hwdist` varchar(100) DEFAULT NULL,
  `ztedist` varchar(100) DEFAULT NULL,
  `allofdist` varchar(100) DEFAULT NULL,
  `ctype` int(11) DEFAULT NULL,
  `cslot` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `df` (`id`,`ctype`,`cslot`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18490 DEFAULT CHARSET=utf8;

/*Table structure for table `daily_flow_clear24` */

DROP TABLE IF EXISTS `daily_flow_clear24`;

CREATE TABLE `daily_flow_clear24` (
  `id_user` varchar(200) DEFAULT NULL,
  `ckey` varchar(200) DEFAULT NULL,
  `platform` int(11) DEFAULT NULL,
  `ctype` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  KEY `dfc` (`id_user`,`ckey`,`ctype`,`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `daily_hotlist_clear24` */

DROP TABLE IF EXISTS `daily_hotlist_clear24`;

CREATE TABLE `daily_hotlist_clear24` (
  `id_item` varchar(100) NOT NULL DEFAULT '' COMMENT '清单对象的标识id(例如:歌曲id,歌手id)',
  `num` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `degree` varchar(20) DEFAULT NULL,
  `dtime` datetime DEFAULT NULL COMMENT '行为发生时间',
  KEY `dhc` (`id_item`,`dtime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `song` */

DROP TABLE IF EXISTS `song`;

CREATE TABLE `song` (
  `id` int(11) NOT NULL COMMENT '歌曲标识id',
  `resid` varchar(50) DEFAULT NULL COMMENT '资源ID，全局唯一(例如:dvd568698)',
  `cname` varchar(200) NOT NULL COMMENT '歌曲名称',
  `cname_len` int(11) DEFAULT NULL COMMENT '名称长度',
  `abbr` varchar(50) DEFAULT NULL COMMENT '名称缩写',
  `id_artist` int(11) unsigned DEFAULT NULL COMMENT '艺人id(指向artist表)',
  `cartist` varchar(100) DEFAULT NULL COMMENT '艺人名称，可以有多个艺人',
  `vtype` int(11) DEFAULT NULL COMMENT '曲目类型 1:卡拉OK 2:MV 3:演唱会',
  `clanguage` int(50) DEFAULT NULL COMMENT '歌曲语种(1:国语 2:粤语 3:欧美 4:日韩 5:其它)',
  `pic` varchar(200) DEFAULT NULL COMMENT '相关图片：MV海报，演唱会海报',
  `csort` int(11) unsigned DEFAULT NULL COMMENT '默认排序',
  `cline` int(11) DEFAULT NULL,
  `dtime` datetime DEFAULT NULL COMMENT '歌曲入库时间',
  PRIMARY KEY (`id`),
  KEY `cso` (`id`,`abbr`,`id_artist`,`vtype`,`clanguage`,`csort`,`cline`,`dtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `song_mp3` */

DROP TABLE IF EXISTS `song_mp3`;

CREATE TABLE `song_mp3` (
  `id` int(11) NOT NULL COMMENT '歌曲标识id',
  `resid` varchar(50) DEFAULT NULL COMMENT '资源ID，全局唯一(例如:dvd568698)',
  `cname` varchar(200) NOT NULL COMMENT '歌曲名称',
  `cname_len` int(11) DEFAULT NULL COMMENT '名称长度',
  `abbr` varchar(50) DEFAULT NULL COMMENT '名称缩写',
  `id_artist` int(11) unsigned DEFAULT NULL COMMENT '艺人id(指向artist表)',
  `cartist` varchar(100) DEFAULT NULL COMMENT '艺人名称，可以有多个艺人',
  `carea` varchar(50) DEFAULT NULL COMMENT '曲目类型 1:卡拉OK 2:MV 3:演唱会',
  `clanguage` int(50) DEFAULT NULL COMMENT '歌曲语种(1:国语 2:粤语 3:欧美 4:日韩 5:其它)',
  `company` varchar(100) DEFAULT NULL,
  `cage` int(10) unsigned zerofill DEFAULT NULL,
  `ctag` text,
  `pic` varchar(200) DEFAULT NULL COMMENT '相关图片：MV海报，演唱会海报',
  `csort` int(11) unsigned DEFAULT NULL COMMENT '默认排序',
  `dtime` datetime DEFAULT NULL COMMENT '歌曲入库时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `song_res` */

DROP TABLE IF EXISTS `song_res`;

CREATE TABLE `song_res` (
  `id_song` int(11) NOT NULL,
  `cres` varchar(100) NOT NULL,
  `platform` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000',
  KEY `sr` (`id_song`,`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ui_pub` */

DROP TABLE IF EXISTS `ui_pub`;

CREATE TABLE `ui_pub` (
  `pid` int(11) DEFAULT NULL COMMENT '页面标识id',
  `nid` int(11) DEFAULT NULL COMMENT '子节点编号：1，2，3...',
  `zindex` int(11) DEFAULT NULL COMMENT '显示层号，默认z-index=0',
  `x` int(11) DEFAULT NULL COMMENT '相对：左边距，px',
  `y` int(11) DEFAULT NULL COMMENT '相对：上边距，px',
  `w` int(11) DEFAULT NULL COMMENT '宽：px',
  `h` int(11) DEFAULT NULL COMMENT '高：px',
  `w2` int(11) DEFAULT NULL,
  `h2` int(11) DEFAULT NULL,
  `cfocus` varchar(200) DEFAULT NULL,
  `pic` varchar(200) DEFAULT NULL COMMENT '区域显示图片',
  `cback` int(11) DEFAULT NULL,
  `onclick_type` int(11) DEFAULT NULL COMMENT '点击后触发行为：弹框，跳转，播放，动画',
  `curl` varchar(250) DEFAULT NULL COMMENT '跳转目标url,如果点击行为弹框,本条无用',
  `p1` varchar(250) DEFAULT NULL COMMENT '通用参数1，传递到url，如歌曲id',
  `p2` varchar(250) DEFAULT NULL COMMENT '通用参数2，传递到url',
  `p3` varchar(250) DEFAULT NULL COMMENT '通用参数3：传递到url',
  KEY `up` (`pid`,`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ui_pub_enter` */

DROP TABLE IF EXISTS `ui_pub_enter`;

CREATE TABLE `ui_pub_enter` (
  `pos` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `w` int(11) DEFAULT NULL,
  `h` int(11) DEFAULT NULL,
  `curl` varchar(200) DEFAULT NULL,
  `ckey` varchar(50) DEFAULT NULL,
  `pic` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_collect` */

DROP TABLE IF EXISTS `user_collect`;

CREATE TABLE `user_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(100) NOT NULL DEFAULT '' COMMENT '用户标识id(指向user_info表)',
  `item_type` int(11) NOT NULL COMMENT '清单对象类型，如:歌曲,歌手',
  `id_item` varchar(100) NOT NULL DEFAULT '' COMMENT '清单对象的标识id(例如:歌曲id,歌手id)',
  `csort` int(11) DEFAULT NULL COMMENT '精确控序',
  `dtime` datetime DEFAULT NULL COMMENT '行为发生时间',
  PRIMARY KEY (`id`),
  KEY `collect` (`id_user`,`item_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=448701 DEFAULT CHARSET=utf8 COMMENT='用户收藏';

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(200) DEFAULT NULL COMMENT '用户ID',
  `preferBackG` varchar(50) DEFAULT NULL,
  `preferPlayer` varchar(50) DEFAULT NULL,
  `preferLoad` int(11) DEFAULT NULL,
  `platform` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '用户初次登入时间',
  PRIMARY KEY (`id`),
  KEY `user_q` (`id_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1697140 DEFAULT CHARSET=utf8;

/*Table structure for table `user_list` */

DROP TABLE IF EXISTS `user_list`;

CREATE TABLE `user_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(100) NOT NULL DEFAULT '' COMMENT '用户标识id(指向user_info表)',
  `item_type` int(11) NOT NULL COMMENT '清单对象类型，如:歌曲,歌手',
  `id_item` varchar(100) NOT NULL DEFAULT '' COMMENT '清单对象的标识id(例如:歌曲id,歌手id)',
  `csort` int(11) DEFAULT NULL COMMENT '精确控序',
  `dtime` datetime DEFAULT NULL COMMENT '行为发生时间',
  PRIMARY KEY (`id`),
  KEY `checked` (`id_user`,`item_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2059877 DEFAULT CHARSET=utf8 COMMENT='用户播放列表';

/*Table structure for table `user_list_continue` */

DROP TABLE IF EXISTS `user_list_continue`;

CREATE TABLE `user_list_continue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(100) NOT NULL,
  `item_type` int(11) DEFAULT NULL,
  `id_item` varchar(100) NOT NULL,
  `curtime` int(11) DEFAULT NULL,
  `playtime` int(11) DEFAULT NULL,
  `dtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_pay_doc_sxdx029` */

DROP TABLE IF EXISTS `user_pay_doc_sxdx029`;

CREATE TABLE `user_pay_doc_sxdx029` (
  `uid` varchar(100) DEFAULT NULL,
  `paystr` varchar(500) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `cflag` int(11) NOT NULL DEFAULT '0',
  KEY `tmp` (`uid`,`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_pay_history` */

DROP TABLE IF EXISTS `user_pay_history`;

CREATE TABLE `user_pay_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户支付id',
  `ip` varchar(20) DEFAULT NULL,
  `uid` varchar(100) NOT NULL,
  `fee` int(11) DEFAULT NULL,
  `paytime` datetime DEFAULT NULL COMMENT '用户id(指向user_info表)',
  `exptime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '商品实际收取费用',
  `purchase` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL COMMENT '发生交易时间',
  `sname` varchar(200) DEFAULT NULL,
  `platform` int(11) DEFAULT NULL,
  `stat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=159335 DEFAULT CHARSET=utf8;

/*Table structure for table `user_pay_info` */

DROP TABLE IF EXISTS `user_pay_info`;

CREATE TABLE `user_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户支付id',
  `ip` varchar(20) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL COMMENT '用户id(指向user_info表)',
  `fee` int(11) DEFAULT NULL COMMENT '商品实际收取费用',
  `createtime` datetime DEFAULT NULL COMMENT '商品实际收取费用',
  `purchase` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `id_content` int(11) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `platform` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96611 DEFAULT CHARSET=utf8;

/*Table structure for table `user_pay_val` */

DROP TABLE IF EXISTS `user_pay_val`;

CREATE TABLE `user_pay_val` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户支付id',
  `ip` varchar(20) DEFAULT NULL,
  `uid` varchar(100) NOT NULL,
  `fee` int(11) DEFAULT NULL,
  `paytime` datetime DEFAULT NULL COMMENT '用户id(指向user_info表)',
  `exptime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '商品实际收取费用',
  `purchase` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL COMMENT '发生交易时间',
  `sname` varchar(200) DEFAULT NULL,
  `platform` int(11) DEFAULT NULL,
  `stat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `temp` (`uid`,`purchase`,`platform`,`stat`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=223395 DEFAULT CHARSET=utf8;

/*Table structure for table `user_permit` */

DROP TABLE IF EXISTS `user_permit`;

CREATE TABLE `user_permit` (
  `id_user` varchar(255) NOT NULL,
  `lean` varchar(255) DEFAULT NULL,
  `permit` varchar(255) DEFAULT NULL,
  `remarks` varchar(500) DEFAULT NULL,
  `vtime` datetime DEFAULT NULL,
  KEY `upm` (`id_user`,`lean`,`permit`,`vtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_prefer` */

DROP TABLE IF EXISTS `user_prefer`;

CREATE TABLE `user_prefer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ele` int(11) DEFAULT NULL COMMENT '1:背景 2:播放器 3:loading画面',
  `cname` varchar(50) DEFAULT NULL COMMENT '偏好的具体名称',
  PRIMARY KEY (`id`),
  KEY `upfr` (`ele`,`cname`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Table structure for table `user_recommend` */

DROP TABLE IF EXISTS `user_recommend`;

CREATE TABLE `user_recommend` (
  `item_type` int(11) NOT NULL COMMENT '清单对象类型，如:歌曲,歌手',
  `id_item` varchar(100) NOT NULL DEFAULT '' COMMENT '清单对象的标识id(例如:歌曲id,歌手id)',
  `csort` int(11) DEFAULT NULL COMMENT '精确控序',
  `dtime` datetime DEFAULT NULL COMMENT '行为发生时间',
  KEY `urm` (`item_type`,`id_item`,`csort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
