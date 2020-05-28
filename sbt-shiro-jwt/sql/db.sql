CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT '' COMMENT '用户名',
  `password` varchar(30) DEFAULT NULL COMMENT '登录密码',
  `nickname` varchar(30) DEFAULT '' COMMENT '用户真实姓名',
  `avatar` varchar(255) DEFAULT '' COMMENT '用户头像',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮件',
  `phone_no` varchar(20) DEFAULT '' COMMENT '用户手机号',
  `state` TINYINT DEFAULT 0 COMMENT '用户状态：0:正常状态, 1：用户被锁定',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除状态：-1:已删除 0:正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` varchar(20) DEFAULT NULL COMMENT '角色标识程序中判断使用,如"admin"',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除状态：-1:已删除 0:正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) DEFAULT NULL COMMENT '父编号,本权限可能是该父编号权限的子权限',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view',
  `resource_type` varchar(20) DEFAULT NULL COMMENT '资源类型，[menu|button]',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径 如：/userinfo/list',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除状态：-1:已删除 0:正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`) USING BTREE,
  KEY `fk_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `fk_role_id` (`role_id`) USING BTREE,
  KEY `fk_permission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone_no`, `state`, `deleted`, `create_time`, `last_update_time`)
VALUES
	(1, 'zhangsan', '123456', '张三', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '123@qq.com', '12122311234', '0', '0', '2019-08-07 11:21:00', '2019-08-07 11:21:00'),
	(2, 'lisi', '123456', '李四', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '123@qq.com', '12122311234', '0', '0', '2019-08-07 11:21:00', '2019-08-07 11:21:00'),
	(3, 'wangwu', '123456', '王五', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '123@qq.com', '12122311234', '0', '0', '2019-08-14 09:52:04', '2019-08-07 11:21:00'),
	(4, 'chenliu', '123456', '陈六', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '123@qq.com', '12122311234', '0', '0', '2019-08-14 10:11:12', '2019-08-07 11:21:00'),
	(5, 'qianqi', '666666', '钱七', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '123@qq.com', '12122311234', '0', '0', '2019-08-14 10:12:24', '2019-08-07 11:21:00');

INSERT INTO `sys_role` (`id`, `deleted`, `role`, `description`, `create_time`, `last_update_time`)
VALUES
	(1, '0', 'admin', '所有权限都有', '2019-08-07 11:24:00', '2019-08-07 11:24:00'),
	(2, '0', 'operator', '查看权限，新增权限', '2019-08-07 11:24:00', '2019-08-07 11:24:00'),
	(3, '0', 'user', '查看权限', '2019-08-14 22:56:00', '2019-08-07 11:24:00');

INSERT INTO `sys_permission` (`id`, `parent_id`, `permission`, `resource_type`, `url`, `name`, `deleted`, `create_time`, `last_update_time`)
VALUES
	(1, NULL, 'user:selectSysUser', 'menu', '', '用户列表', '0', '2019-08-07 11:21:00', '2019-08-07 11:21:00'),
	(2, 1, 'user:addSysUser', 'button', '', '添加用户', '0', '2019-08-07 11:21:00', '2019-08-07 11:21:00'),
	(3, NULL, 'user:updateSysUser', 'button', '', '更改状态', '0', '2019-08-07 11:21:00', '2019-08-07 11:21:00');

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`)
VALUES
	(1, 1, 1),
	(2, 2, 2),
	(3, 3, 3);

INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`)
VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 2, 1),
	(4, 1, 3),
	(5, 2, 2),
	(6, 3, 1);