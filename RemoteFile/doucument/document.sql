#
# Structure for table "doc_info"
#

DROP TABLE IF EXISTS `doc_info`;
CREATE TABLE `doc_info` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) NOT NULL DEFAULT '',
  `timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` text,
  `filename` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "doc_info"
#

INSERT INTO `doc_info` VALUES (1,'jack','2016-11-17 00:00:00',NULL,'doc.java');

#
# Structure for table "user_info"
#

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `role` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_info"
#

INSERT INTO `user_info` VALUES ('jack','123','operator'),('kate','123','administrator'),('rose','123','browser');
