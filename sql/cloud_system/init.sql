DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'email',
  `mobile` int(11) DEFAULT NULL COMMENT '手机',
  `status` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '状态',
  `create_user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人员ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `pic_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像ID',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统用户表';

SET FOREIGN_KEY_CHECKS = 1;

insert into sys_user values ('1', 'admin', 'admin', 'd633268afedf209e1e4ea0f5f43228a8', null, null, 1, '1', current_timestamp(), null, null, null, null);

-- 创建角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
  `role_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名',
  `role_sign` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人员ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人员ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `dept_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属机构ID',
  `global` tinyint(1) DEFAULT NULL COMMENT '全局',
  `seq` int(255) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色表';

SET FOREIGN_KEY_CHECKS = 1;

-- 人员角色中间表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `role_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;