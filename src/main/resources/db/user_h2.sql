CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `finance_change` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL COMMENT '记账时间',
  `trade_sn` varchar(200) DEFAULT NULL COMMENT '微信支付业务单号',
  `finance_sn` varchar(200) DEFAULT NULL COMMENT '资金流水单号',
  `name` varchar(45) DEFAULT NULL COMMENT '业务名称',
  `bs_type` tinyint(4) DEFAULT NULL COMMENT '1:交易\n2:扣除交易手续费\n3:未知类型',
  `type` tinyint(4) DEFAULT NULL COMMENT '1:收入\n2:支出',
  `count` bigint(20) DEFAULT NULL COMMENT '金额，单位为分',
  `left_count` bigint(20) DEFAULT NULL COMMENT '账户结余',
  `apply_name` varchar(200) DEFAULT NULL COMMENT '资金变更提交申请',
  `remark` text COMMENT '备注',
  `bs_sn` varchar(200) DEFAULT NULL COMMENT '业务凭证',
  `input_file_id` bigint(20) DEFAULT NULL COMMENT '输入文件的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `task_url` VARCHAR(200) NULL COMMENT '外部链接',
  `gmt_create` DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`id`));

CREATE TABLE `input_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名称',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：\n1:导入中\n2:失败\n3:成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
