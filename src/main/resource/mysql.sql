1.mysql -uroot -p
2.CREATE DATABASE cloud_message DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
3.use cloud_message;
4.CREATE TABLE `users` (  
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20)  NOT NULL,
  `user_friends` varchar(500) COMMENT '用户的好友都存储在这里，用逗号分隔，用户好友最大限制xx人，避免该字段存不下',
  `user_groups` varchar(200) COMMENT  '用户加入的群组，逗号分隔，比如 12345,23456' ,
  `description` varchar(50) COMMENT '备注信息，有时不想改变表结构但有需要的存储某些信息，派的上用场',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

5.CREATE TABLE `groups` (  
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(20),
  `group_threshold` int(8) COMMENT '群组用户数量的上限，超过后无法加入',
  `description` varchar(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
