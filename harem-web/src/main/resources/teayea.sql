# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 119.254.102.238 (MySQL 5.5.38-0+wheezy1-log)
# Database: teayea_test
# Generation Time: 2015-06-09 02:44:18 +0000
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of table address
# ------------------------------------------------------------

CREATE TABLE `address` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `addressee` varchar(255) DEFAULT NULL COMMENT '收件人',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '地级市（联）',
  `region` varchar(32) DEFAULT NULL COMMENT '县或区（联）',
  `post_code` varchar(32) DEFAULT NULL COMMENT '邮编',
  `address` varchar(255) DEFAULT NULL COMMENT '地址全称',
  `tel` varchar(11) DEFAULT NULL COMMENT '手机号',
  `created_on` datetime DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  `is_default` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table alipay_direct_trade
# ------------------------------------------------------------

CREATE TABLE `alipay_direct_trade` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户网站唯一订单号',
  `subject` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `payment_type` varchar(4) DEFAULT NULL COMMENT '支付类型',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `trade_status` varchar(255) DEFAULT NULL COMMENT '交易状态',
  `gmt_create` datetime DEFAULT NULL COMMENT '交易创建时间',
  `gmt_payment` datetime DEFAULT NULL COMMENT '交易付款时间',
  `gmt_close` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `refund_status` varchar(256) DEFAULT NULL COMMENT '退款状态',
  `gmt_refund` datetime DEFAULT NULL COMMENT '退款时间',
  `seller_email` varchar(100) DEFAULT NULL COMMENT '卖家支付宝账号',
  `buyer_email` varchar(100) DEFAULT NULL COMMENT '买家支付宝账号',
  `seller_id` varchar(30) DEFAULT NULL COMMENT '卖家支付宝账户号',
  `buyer_id` varchar(30) DEFAULT NULL COMMENT '买家支付宝账户号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `total_fee` decimal(10,2) DEFAULT NULL COMMENT '交易金额',
  `quantity` int(11) DEFAULT NULL COMMENT '购买数量',
  `body` varchar(400) DEFAULT NULL COMMENT '商品描述',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `is_total_fee_adjust` varchar(1) DEFAULT NULL COMMENT '是否调整总价',
  `use_coupon` varchar(1) DEFAULT NULL COMMENT '是否使用红包买家',
  `extra_common_param` varchar(256) DEFAULT NULL COMMENT '公用回传参数',
  `out_channel_type` varchar(256) DEFAULT NULL COMMENT '支付渠道组合信息',
  `out_channel_amount` varchar(256) DEFAULT NULL COMMENT '支付金额组合信息',
  `out_channel_inst` varchar(256) DEFAULT NULL COMMENT '实际支付渠道',
  `business_scene` varchar(256) DEFAULT NULL COMMENT '是否扫码支付',
  `notify_time` datetime DEFAULT NULL COMMENT '通知时间',
  `notify_type` varchar(256) DEFAULT NULL COMMENT '通知类型',
  `notify_id` varchar(256) DEFAULT NULL COMMENT '通知校验 ID',
  `sign_type` varchar(256) DEFAULT NULL COMMENT '签名方式',
  `sign` varchar(256) DEFAULT NULL COMMENT '签名',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付宝即时到账支付';

# Dump of table category
# ------------------------------------------------------------

CREATE TABLE `category` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `background_img` varchar(256) DEFAULT NULL COMMENT '背景图',
  `parent_id` varchar(32) DEFAULT '' COMMENT '父级分类id',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `parentid` (`parent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='品类';

# Dump of table cd_key
# ------------------------------------------------------------

CREATE TABLE `cd_key` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) DEFAULT NULL COMMENT '邀请码',
  `used` int(1) DEFAULT '0' COMMENT '是否已使用（0：未试用；1：已使用）',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) DEFAULT '1' COMMENT '记标数据是否被删除（1 正常， 0 删除， 默认为1）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table charge
# ------------------------------------------------------------

CREATE TABLE `charge` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `basic_price` decimal(10,2) NOT NULL COMMENT '基础价格',
  `per_price` decimal(10,2) NOT NULL COMMENT '价格/kg',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='其它收费项';

# Dump of table craft
# ------------------------------------------------------------

CREATE TABLE `craft` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品类',
  `ratio` decimal(10,2) NOT NULL COMMENT '制茶比例',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='制茶工艺';

# Dump of table express
# ------------------------------------------------------------

CREATE TABLE `express` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `per_price` decimal(10,2) NOT NULL COMMENT '价格/kg',
  `basic_price` decimal(10,2) NOT NULL COMMENT '起步价',
  `basic_weight` int(11) NOT NULL COMMENT '基础重量',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='快递';

# Dump of table goods
# ------------------------------------------------------------

CREATE TABLE `goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单主键',
  `money` decimal(10,2) DEFAULT NULL COMMENT '商品总金额',
  `goods_type` int(1) DEFAULT NULL COMMENT '货物类型（1、礼品&自饮 2、原茶批发 3、茶投资）',
  `goods_status` int(1) DEFAULT NULL COMMENT '货物状态（1、现货，2、预定）',
  `goods_weight` decimal(10,2) DEFAULT NULL COMMENT '货物重量(kg)',
  `mountain_season_id` varchar(32) DEFAULT NULL COMMENT '山头时令主键',
  `maker_craft_id` varchar(32) DEFAULT NULL COMMENT '制茶师工艺主键',
  `process_id` varchar(32) DEFAULT NULL COMMENT '加工工序主键',
  `pack_id` varchar(32) DEFAULT NULL COMMENT '包装主键',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货物';

# Dump of table maker
# ------------------------------------------------------------

CREATE TABLE `maker` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='制茶师';

# Dump of table maker_craft
# ------------------------------------------------------------

CREATE TABLE `maker_craft` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `maker_id` varchar(32) NOT NULL DEFAULT '' COMMENT '制茶师id',
  `craft_id` varchar(32) NOT NULL DEFAULT '' COMMENT '工艺id',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `maker_id` (`maker_id`),
  KEY `craft_id` (`craft_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='制茶师-工艺';

# Dump of table maker_mountain
# ------------------------------------------------------------

CREATE TABLE `maker_mountain` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `maker_id` varchar(32) NOT NULL DEFAULT '' COMMENT '制茶师id',
  `mountain_id` varchar(32) NOT NULL DEFAULT '' COMMENT '山头id',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `mid` (`mountain_id`),
  KEY `maker_id` (`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='制茶师-山头';

# Dump of table message_code
# ------------------------------------------------------------

CREATE TABLE `message_code` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `tel` varchar(32) DEFAULT NULL COMMENT '电话号码',
  `code` varchar(255) DEFAULT NULL,
  `expires` datetime DEFAULT NULL COMMENT '过期时间',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '记标数据是否被删除（1 正常， 0 删除， 默认为1）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table mountain
# ------------------------------------------------------------

CREATE TABLE `mountain` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品类',
  `origin_id` varchar(32) NOT NULL DEFAULT '' COMMENT '产地',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态(1、正常，0、已删除)',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `origin_id` (`origin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='山头';

# Dump of table mountain_season
# ------------------------------------------------------------

CREATE TABLE `mountain_season` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `mountain_id` varchar(32) NOT NULL DEFAULT '' COMMENT '山头id',
  `season_id` varchar(32) NOT NULL DEFAULT '' COMMENT '时令id',
  `position` int(1) NOT NULL COMMENT '位置1、山头，2、山腰，3、山脚',
  `price` decimal(10,2) NOT NULL COMMENT '价格/kg',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `onsell` int(1) DEFAULT NULL COMMENT '是否在售（1：在售，2：不在售）',
  `presell` int(1) DEFAULT NULL COMMENT '是否预售（1：预售，2:不预售）',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `mountain_id` (`mountain_id`),
  KEY `season_id` (`season_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table order_seq
# ------------------------------------------------------------

CREATE TABLE `order_seq` (
  `pkid` int(5) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table orders
# ------------------------------------------------------------

CREATE TABLE `orders` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户主键',
  `address_id` varchar(32) DEFAULT NULL COMMENT '地址主键',
  `express_id` varchar(32) DEFAULT NULL COMMENT '快递主键',
  `trade_price` decimal(10,0) DEFAULT '0' COMMENT '交易金额',
  `pay_type` int(1) DEFAULT NULL COMMENT '支付类型（1、支付宝即时支付，2微信支付）',
  `order_status` int(1) NOT NULL DEFAULT '0' COMMENT '订单状态（0：未支付，1：已支付，2：已发货，3、已经收货）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `deliver_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

# Dump of table origin
# ------------------------------------------------------------

CREATE TABLE `origin` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='产地';

# Dump of table origin_category
# ------------------------------------------------------------

CREATE TABLE `origin_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `origin_id` varchar(32) DEFAULT NULL COMMENT '产地ID',
  `category_id` varchar(32) DEFAULT NULL COMMENT '品类ID',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table pack
# ------------------------------------------------------------

CREATE TABLE `pack` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '类别',
  `price` decimal(10,2) NOT NULL COMMENT '价格/kg',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='包装';

# Dump of table process
# ------------------------------------------------------------

CREATE TABLE `process` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品类id',
  `ratio` decimal(10,2) NOT NULL COMMENT '制茶比例',
  `price` decimal(10,5) DEFAULT NULL COMMENT '价格',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加工工序';

# Dump of table region
# ------------------------------------------------------------

CREATE TABLE `region` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '地区名称',
  `area_code` varchar(32) DEFAULT NULL COMMENT '区号',
  `post_code` varchar(32) DEFAULT NULL COMMENT '邮编',
  `level` int(1) DEFAULT NULL COMMENT '级别',
  `code` varchar(255) DEFAULT NULL COMMENT '级别编码',
  `parent` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '记标数据是否被删除（1 正常， 0 删除， 默认为1）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table season
# ------------------------------------------------------------

CREATE TABLE `season` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `intro` varchar(100) NOT NULL DEFAULT '' COMMENT '简介',
  `introduction` text COMMENT '详细介绍',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='时令';

# Dump of table shopping_cart
# ------------------------------------------------------------

CREATE TABLE `shopping_cart` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户主键',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '货物主键',
  `created_on` datetime NOT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';

# Dump of table sms
# ------------------------------------------------------------

CREATE TABLE `sms` (
  `id` varchar(32) NOT NULL,
  `phone_no` varchar(20) NOT NULL,
  `content` varchar(70) NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '1',
  `publish_status` int(11) NOT NULL DEFAULT '1' COMMENT '是否发送成功 0 成功， 1 未发送， -1 不成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table sms_template
# ------------------------------------------------------------

CREATE TABLE `sms_template` (
  `id` varchar(32) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `content` varchar(70) NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `alias_UNIQUE` (`alias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table sys_user
# ------------------------------------------------------------

CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '记标数据是否被删除（1 正常， 0 删除， 默认为1）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table user
# ------------------------------------------------------------

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `tel` varchar(32) DEFAULT NULL COMMENT '注册手机',
  `gender` int(1) NOT NULL DEFAULT '0' COMMENT '性别（0：无，1：男，2：女）',
  `created_on` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '记标数据是否被删除（1 正常， 0 删除， 默认为1）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table wechat_direct_trade
# ------------------------------------------------------------

CREATE TABLE `wechat_direct_trade` (
  `id` varchar(32) NOT NULL,
  `orders_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `appid` varchar(64) DEFAULT NULL COMMENT '公众帐号ID',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '商户号',
  `device_info` varchar(64) DEFAULT NULL COMMENT '设备号',
  `nonce_str` varchar(128) DEFAULT NULL COMMENT '随机字符串',
  `sign` varchar(64) DEFAULT NULL COMMENT '签名',
  `body` varchar(64) DEFAULT NULL COMMENT '商品描述',
  `detail` varchar(16384) DEFAULT NULL COMMENT '商品详情',
  `attach` varchar(255) DEFAULT NULL COMMENT '附加数据(商户携带订单的自定义数据)',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户订单号',
  `fee_type` varchar(32) DEFAULT NULL COMMENT '货币类型',
  `total_fee` int(11) DEFAULT NULL COMMENT '总金额(单位分)',
  `spbill_create_ip` varchar(32) DEFAULT NULL COMMENT '终端IP',
  `time_start` datetime DEFAULT NULL COMMENT '交易起始时间',
  `time_expire` datetime DEFAULT NULL COMMENT '交易结束时间',
  `goods_tag` varchar(64) DEFAULT NULL COMMENT '商品标记',
  `notify_url` varchar(512) DEFAULT NULL COMMENT '通知地址',
  `trade_type` varchar(32) DEFAULT NULL COMMENT '交易类型',
  `product_id` varchar(64) DEFAULT NULL COMMENT '商品ID',
  `openid` varchar(256) DEFAULT NULL COMMENT '用户标识',
  `prepay_id` varchar(128) DEFAULT NULL COMMENT '预支付交易会话标识',
  `code_url` varchar(128) DEFAULT NULL COMMENT '二维码链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table wechat_pay_notice
# ------------------------------------------------------------

CREATE TABLE `wechat_pay_notice` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `return_code` varchar(32) DEFAULT NULL COMMENT '返回状态码',
  `return_msg` varchar(256) DEFAULT NULL COMMENT '返回信息',
  `appid` varchar(64) DEFAULT NULL COMMENT '公众账号ID',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '商户号',
  `device_info` varchar(64) DEFAULT NULL COMMENT '设备号',
  `nonce_str` varchar(64) DEFAULT NULL COMMENT '随机字符串',
  `sign` varchar(64) DEFAULT NULL COMMENT '签名',
  `result_code` varchar(32) DEFAULT NULL COMMENT '业务结果',
  `err_code` varchar(64) DEFAULT NULL COMMENT '错误代码',
  `err_code_des` varchar(256) DEFAULT NULL COMMENT '错误代码描述',
  `openid` varchar(256) DEFAULT NULL COMMENT '用户标识',
  `is_subscribe` varchar(2) DEFAULT NULL COMMENT '是否关注公众账号',
  `trade_type` varchar(32) DEFAULT NULL COMMENT '交易类型',
  `bank_type` varchar(32) DEFAULT NULL COMMENT '付款银行',
  `total_fee` int(255) DEFAULT NULL,
  `fee_type` varchar(16) DEFAULT NULL COMMENT '货币种类',
  `cash_fee` int(16) DEFAULT NULL COMMENT '现金支付金额',
  `cash_fee_type` varchar(32) DEFAULT NULL COMMENT '现金支付货币类型',
  `coupon_fee` int(255) DEFAULT NULL COMMENT '代金券或立减优惠金额',
  `coupon_count` int(255) DEFAULT NULL COMMENT '代金券或立减优惠使用数量',
  `coupon_batch_id_$n` varchar(40) DEFAULT NULL,
  `coupon_id_$n` varchar(40) DEFAULT NULL COMMENT '代金券或立减优惠批次ID',
  `coupon_fee_$n` int(255) DEFAULT NULL COMMENT '单个代金券或立减优惠支付金额',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '微信支付订单号',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户订单号',
  `attach` varchar(256) DEFAULT NULL COMMENT '商家数据包',
  `time_end` varchar(28) DEFAULT NULL COMMENT '支付完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table wechat_scan_pay_res
# ------------------------------------------------------------

CREATE TABLE `wechat_scan_pay_res` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `return_code` varchar(32) DEFAULT NULL COMMENT '返回状态码',
  `return_msg` varchar(256) DEFAULT NULL COMMENT '返回信息',
  `appid` varchar(64) DEFAULT NULL COMMENT '公众账号ID',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '商户号',
  `device_info` varchar(64) DEFAULT NULL COMMENT '设备号',
  `nonce_str` varchar(64) DEFAULT NULL COMMENT '随机字符串',
  `sign` varchar(64) DEFAULT NULL COMMENT '签名',
  `result_code` varchar(32) DEFAULT NULL COMMENT '业务结果',
  `err_code` varchar(64) DEFAULT NULL COMMENT '错误代码',
  `err_code_des` varchar(256) DEFAULT NULL COMMENT '错误代码描述',
  `trade_type` varchar(32) DEFAULT NULL COMMENT '交易类型',
  `prepay_id` varchar(128) DEFAULT NULL COMMENT '预支付交易会话标识',
  `code_url` varchar(128) DEFAULT NULL COMMENT '二维码链接',
  `code_url_time` datetime DEFAULT NULL COMMENT '二维码有效时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

# ---------------------- 2015-06-09 导出分割线--------------------------------
# 表结构改动sql放下边，注明修改人，日期，具体sql

