CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(200) NOT NULL COMMENT 'mobile 字段',
  `password` varchar(200) NOT NULL COMMENT 'password 字段',
  PRIMARY KEY (`id`)
);

CREATE TABLE `VC` (
  `v` varchar(100) DEFAULT NULL COMMENT 'v 字段',
  `c` char(100) DEFAULT NULL COMMENT 'c 字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;