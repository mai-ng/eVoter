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
CREATE DATABASE IF NOT EXISTS `evoter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `evoter`;


-- Dumping structure for table evoter.answer
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(10) unsigned NOT NULL,
  `answer_text` char(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_FKIndex1` (`question_id`),
  CONSTRAINT `FK_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.answer: ~6 rows (approximately)
DELETE FROM `answer`;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` (`id`, `question_id`, `answer_text`) VALUES
	(1, 3, '1'),
	(2, 3, '2'),
	(3, 3, '0'),
	(4, 3, 'unlimited'),
	(7, 2, 'add a new answer'),
	(8, 2, 'add a new answer');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;


-- Dumping structure for table evoter.question
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.question: ~3 rows (approximately)
DELETE FROM `question`;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`id`, `question_type_id`, `session_id`, `question_text`) VALUES
	(1, 2, 1, 'Interface can implement an interface?'),
	(2, 2, 1, 'Abstract can implement an interface?'),
	(3, 1, 1, 'An class can implement how many interface?');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;


-- Dumping structure for table evoter.question_type
CREATE TABLE IF NOT EXISTS `question_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_type_value` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.question_type: ~5 rows (approximately)
DELETE FROM `question_type`;
/*!40000 ALTER TABLE `question_type` DISABLE KEYS */;
INSERT INTO `question_type` (`id`, `question_type_value`) VALUES
	(1, 'Multiple Choice'),
	(2, 'Yes/No'),
	(3, 'Agree/Disagree'),
	(4, 'Slider'),
	(5, 'Match');
/*!40000 ALTER TABLE `question_type` ENABLE KEYS */;


-- Dumping structure for table evoter.session
CREATE TABLE IF NOT EXISTS `session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subject_id` int(10) unsigned NOT NULL,
  `name` char(100) NOT NULL,
  `creation_date` datetime NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_FKIndex1` (`subject_id`),
  CONSTRAINT `FK_session_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.session: ~9 rows (approximately)
DELETE FROM `session`;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` (`id`, `subject_id`, `name`, `creation_date`, `is_active`) VALUES
	(1, 1, 'subject_1_session_1', '2013-12-28 00:00:00', 1),
	(2, 1, 'subject_1_session_2', '2013-12-28 12:50:24', 0),
	(3, 1, 'subject_1_session_3', '2013-12-28 12:50:24', 0),
	(4, 2, 'subject_2_session_4\r\n', '2013-12-28 12:50:24', 0),
	(5, 2, 'subject_2_session_5', '2013-12-28 12:50:24', 0),
	(6, 2, 'subject_2_session_6', '2013-12-28 12:50:24', 0),
	(7, 3, 'subject_3_session_7', '2013-12-28 12:50:24', 0),
	(8, 3, 'subject_3_session_8', '2013-12-28 12:50:24', 0),
	(9, 3, 'subject_3_session_9', '2013-12-28 12:50:24', 0);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


-- Dumping structure for table evoter.session_user
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

-- Dumping data for table evoter.session_user: ~8 rows (approximately)
DELETE FROM `session_user`;
/*!40000 ALTER TABLE `session_user` DISABLE KEYS */;
INSERT INTO `session_user` (`user_id`, `session_id`, `delete_indicator`, `accept_stt`) VALUES
	(1, 1, 1, 1),
	(2, 2, 0, 1),
	(3, 1, 0, 0),
	(3, 2, 1, 1),
	(4, 1, 0, 1),
	(4, 2, 0, 1),
	(21, 5, 0, 0),
	(23, 4, 0, 0);
/*!40000 ALTER TABLE `session_user` ENABLE KEYS */;


-- Dumping structure for table evoter.statistics
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

-- Dumping data for table evoter.statistics: ~0 rows (approximately)
DELETE FROM `statistics`;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;


-- Dumping structure for table evoter.subject
CREATE TABLE IF NOT EXISTS `subject` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` char(100) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.subject: ~14 rows (approximately)
DELETE FROM `subject`;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` (`id`, `title`, `creation_date`) VALUES
	(1, 'Object Oriented Programming', '2013-12-28 12:50:24'),
	(2, 'Testing Metrics', '2013-12-28 12:50:24'),
	(3, 'Software Engineering', '2013-12-28 12:50:24'),
	(5, 'test subject lalala', '2013-12-31 00:00:00'),
	(6, 'test subject lalala', '2013-12-31 00:00:00'),
	(7, 'test subject lalala', '2013-12-31 00:00:00'),
	(8, 'test subject lalala', '2013-12-31 00:00:00'),
	(9, 'test subject lalala', '2013-12-31 00:00:00'),
	(12, 'test subject lalala', '2013-12-31 00:00:00'),
	(13, 'test subject lalala', '2013-12-31 00:00:00'),
	(14, 'test subject lalala', '2013-12-31 00:00:00'),
	(16, 'test subject lalala', '2013-12-31 00:00:00'),
	(18, 'test subject lalala', '2013-12-31 00:00:00'),
	(20, 'test subject lalala', '2013-12-31 00:00:00');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;


-- Dumping structure for table evoter.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_id` int(10) unsigned NOT NULL,
  `email_address` char(100) NOT NULL,
  `username` char(30) NOT NULL,
  `passwd` char(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_FKIndex1` (`user_type_id`),
  CONSTRAINT `FK_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user: ~7 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_type_id`, `email_address`, `username`, `passwd`) VALUES
	(1, 2, 'paul.gibson@telecom-sudparis.eu', 'paul', '12345678'),
	(2, 2, 'jean.luc@telecom-sudparis.eu', 'jean', '12345678'),
	(3, 3, 'nguyen.van@telecom-sudparis.eu', 'nvluong', '12345678'),
	(4, 3, 'nguyen.mai@telecom-sudparis.eu', 'ntmai', '12345678'),
	(21, 3, 'diem.bt@telecom-sudparis.eu', 'btdiem', '1234567890'),
	(22, 2, 'tuan@yahoo.com', 'tuan', '123456789'),
	(23, 1, 'someone@gmail.com', 'so', '98adsajdsa');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table evoter.user_subject
CREATE TABLE IF NOT EXISTS `user_subject` (
  `subject_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`subject_id`,`user_id`),
  KEY `user_subject_FKIndex1` (`subject_id`),
  KEY `FK_user_subject_user` (`user_id`),
  CONSTRAINT `FK_user_subject_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `FK_user_subject_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user_subject: ~9 rows (approximately)
DELETE FROM `user_subject`;
/*!40000 ALTER TABLE `user_subject` DISABLE KEYS */;
INSERT INTO `user_subject` (`subject_id`, `user_id`) VALUES
	(1, 1),
	(1, 3),
	(1, 4),
	(2, 2),
	(2, 3),
	(2, 4),
	(3, 1),
	(3, 3),
	(3, 4);
/*!40000 ALTER TABLE `user_subject` ENABLE KEYS */;


-- Dumping structure for table evoter.user_type
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_value` char(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user_type: ~3 rows (approximately)
DELETE FROM `user_type`;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` (`id`, `user_type_value`) VALUES
	(1, 'Secretary'),
	(2, 'Teacher'),
	(3, 'Student');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
