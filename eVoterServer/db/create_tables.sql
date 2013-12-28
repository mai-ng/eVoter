-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.14 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.1.0.4545
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for evoter
DROP DATABASE IF EXISTS `evoter`;
CREATE DATABASE IF NOT EXISTS `evoter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `evoter`;


-- Dumping structure for table evoter.answer
DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(10) unsigned NOT NULL,
  `answer_text` char(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_FKIndex1` (`question_id`),
  CONSTRAINT `FK_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.question
DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_type_id` int(10) unsigned NOT NULL,
  `session_id` int(10) unsigned NOT NULL,
  `question_text` char(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_FKIndex1` (`session_id`),
  KEY `question_FKIndex2` (`question_type_id`),
  CONSTRAINT `FK_question_question_type` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`),
  CONSTRAINT `FK_question_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.question_type
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE IF NOT EXISTS `question_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_type_value` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.session
DROP TABLE IF EXISTS `session`;
CREATE TABLE IF NOT EXISTS `session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subject_id` int(10) unsigned NOT NULL,
  `name` char(100) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `session_FKIndex1` (`subject_id`),
  CONSTRAINT `FK_session_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.session_user
DROP TABLE IF EXISTS `session_user`;
CREATE TABLE IF NOT EXISTS `session_user` (
  `user_id` int(10) unsigned NOT NULL,
  `session_id` int(10) unsigned NOT NULL,
  `delete_indicator` tinyint(1) unsigned zerofill NOT NULL,
  `accept_stt` tinyint(1) unsigned zerofill NOT NULL,
  PRIMARY KEY (`user_id`,`session_id`),
  KEY `session_user_FKIndex1` (`session_id`),
  KEY `session_user_FKIndex2` (`user_id`),
  CONSTRAINT `FK_session_user_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`),
  CONSTRAINT `FK_session_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.statistics
DROP TABLE IF EXISTS `statistics`;
CREATE TABLE IF NOT EXISTS `statistics` (
  `statistics_value` char(255) DEFAULT NULL,
  `session_id` int(10) unsigned NOT NULL,
  `question_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`question_id`,`session_id`),
  KEY `statistics_FKIndex1` (`question_id`),
  KEY `statistics_FKIndex2` (`session_id`),
  CONSTRAINT `FK_statistics_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FK_statistics_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.subject
DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` char(100) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_id` int(10) unsigned NOT NULL,
  `email_address` char(100) NOT NULL,
  `username` char(30) NOT NULL,
  `passwd` char(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_FKIndex1` (`user_type_id`),
  CONSTRAINT `FK_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.user_subject
DROP TABLE IF EXISTS `user_subject`;
CREATE TABLE IF NOT EXISTS `user_subject` (
  `subject_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`subject_id`,`user_id`),
  KEY `user_subject_FKIndex1` (`subject_id`),
  KEY `FK_user_subject_user` (`user_id`),
  CONSTRAINT `FK_user_subject_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `FK_user_subject_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table evoter.user_type
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_value` char(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
