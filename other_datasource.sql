DROP TABLE IF EXISTS `other_datasource`;
CREATE TABLE `other_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datasource_name` varchar(64) DEFAULT NULL,
  `database_ip` varchar(64) DEFAULT NULL,
  `database_port` varchar(64) DEFAULT NULL,
  `database_name` varchar(64) DEFAULT NULL,
  `database_username` varchar(64) DEFAULT NULL,
  `database_password` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
