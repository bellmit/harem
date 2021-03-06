SELECT * FROM membercenter.ha_menu WHERE NAME = '分销管理' AND project_code = 1
SELECT * FROM membercenter.ha_role WHERE NAME = '分销管理';

SET @SID = (SELECT id FROM membercenter.ha_menu WHERE NAME = '分销管理' AND project_code = 1);


-- 菜单添加

INSERT INTO membercenter.ha_menu (NAME, url, req_type, TYPE, LEVEL, leaf, parent_id, gmt_created, gmt_modified, STATUS, project_code)
VALUES ('分销代理商管理', '/GF/agent/queryList', 1, 1, 2, 1, @SID, '2016-11-01 11:20:58', '2016-11-01 11:20:58', 1, 1);

SET @MENU = (SELECT id FROM membercenter.ha_menu WHERE NAME = '分销代理商管理' AND project_code = 1);
SET @ROLE = 10011;
-- 订单状态修改菜单权限添加

INSERT INTO `ha_role_menu` ( `ha_menu_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
 VALUES (@MENU,@ROLE,'2016-11-01 11:20:58','2016-11-01 11:20:58',1);

INSERT INTO membercenter.ha_user_role ( `ha_user_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
VALUES (101870,@ROLE,'2015-11-01 10:43:02','2016-11-01 02:07:22',1);
INSERT INTO membercenter.ha_user_role ( `ha_user_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
VALUES (103470,@ROLE,'2015-11-01 10:43:02','2016-11-01 02:07:22',1);
INSERT INTO membercenter.ha_user_role ( `ha_user_id`, `ha_role_id`, `gmt_created`, `gmt_modified`, `status`)
VALUES (103805,@ROLE,'2015-11-01 10:43:02','2016-11-01 02:07:22',1);
