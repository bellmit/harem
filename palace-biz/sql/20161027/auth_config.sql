SELECT * FROM membercenter.ha_menu WHERE NAME = '订单管理' AND project_code = 1

SET @SID = (SELECT id FROM membercenter.ha_menu WHERE NAME = '订单管理' AND project_code = 1);

INSERT INTO membercenter.ha_menu (NAME, url, req_type, TYPE, LEVEL, leaf, parent_id, domain, gmt_created, gmt_modified, STATUS, project_code)
VALUES ('订单状态修改', '/order/status/queryList', 1, 1, 2, 1, @SID, 1200, '2016-06-27 11:20:58', '2016-06-27 11:20:58', 1, 1);

INSERT INTO membercenter.ha_menu (NAME, url, req_type, TYPE, LEVEL, leaf, parent_id, domain, gmt_created, gmt_modified, STATUS, project_code)
VALUES ('修改历史', '/order/status/queryLogList', 1, 1, 2, 1, @SID, 1200, '2016-06-27 11:20:58', '2016-06-27 11:20:58', 1, 1);


INSERT INTO membercenter.ha_role ( `name`, `gmt_created`, `gmt_modified`, `status`,`project_code`)
VALUES ('订单修改','2015-10-24 15:42:30','2016-07-11 02:07:22',1,1);

SET @ROLE = (SELECT id FROM membercenter.ha_role WHERE NAME = '订单修改' AND project_code = 1);

-- 订单状态修改菜单权限添加
SET @MENU = (SELECT id FROM membercenter.ha_menu WHERE NAME = '订单状态修改' AND project_code = 1);

INSERT INTO membercenter.ha_role_menu ( `ha_menu_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
 VALUES (@MENU,@ROLE,'2016-07-11 11:44:18','2016-07-11 03:44:24',1);
-- 修改历史菜单权限添加
SET @MENU = (SELECT id FROM membercenter.ha_menu WHERE NAME = '修改历史' AND project_code = 1);

INSERT INTO membercenter.ha_role_menu ( `ha_menu_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
 VALUES (@MENU,@ROLE,'2016-07-11 11:44:18','2016-07-11 03:44:24',1);

INSERT INTO membercenter.ha_user_role ( `ha_user_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
VALUES (102900,@ROLE,'2015-10-26 10:43:02','2016-07-11 02:07:22',1);
