CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `VC` (
  `v` varchar(100) DEFAULT NULL,
  `c` char(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;