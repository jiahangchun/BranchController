CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `biz_code` varchar(32) ,
--   `delete_mark` tinyint(4) ,
--   `delete_timestamp` bigint(20),
--   `gmt_created` datetime ,
--   `gmt_modified` datetime NOT NULL,
--   `user_id` bigint(20) NOT NULL,
--   `name` varchar(128) NOT NULL,
--   `role_type` tinyint(4) NOT NULL COMMENT '角色 1:普通用户 ，2 管理员',
--   `check_type` tinyint(4) NOT NULL COMMENT '认证来源 1:本地',
--   `status` tinyint(4) NOT NULL COMMENT '是否可用 1:可用 0：不可用',
--   `expire_time` datetime NOT NULL COMMENT '失效时间',
--   `remark` varchar(200) DEFAULT NULL,
--   `last_time` datetime NOT NULL COMMENT '最近登录时间',
  `mobile` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
);
