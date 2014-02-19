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
  `statistics` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_FKIndex1` (`question_id`),
  CONSTRAINT `FK_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.answer: ~14 rows (approximately)
DELETE FROM `answer`;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` (`id`, `question_id`, `answer_text`, `statistics`) VALUES
	(1, 3, '1', NULL),
	(2, 3, '2', NULL),
	(3, 3, '0', NULL),
	(4, 3, 'unlimited', NULL),
	(5, 4, 'My brother', NULL),
	(6, 4, 'He broke his leg.', NULL),
	(7, 4, 'Two hours.', NULL),
	(8, 4, 'It\'s 2 o\'clock.', NULL),
	(9, 2, 'No', '2'),
	(10, 2, 'Yes', '5'),
	(139, 1, 'Yes', '10'),
	(140, 1, 'No', '30'),
	(141, 57, 'input', '10:20:30:10:50'),
	(142, 58, 'slider', '2:3:2:4:7:10');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;


-- Dumping structure for table evoter.question
CREATE TABLE IF NOT EXISTS `question` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_text` char(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `question_type_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `parent_id` int(10) DEFAULT '0',
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_question_user` (`user_id`),
  KEY `FK_question_question_type` (`question_type_id`),
  CONSTRAINT `FK_question_question_type` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_question_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.question: ~10 rows (approximately)
DELETE FROM `question`;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`id`, `question_text`, `creation_date`, `question_type_id`, `user_id`, `parent_id`, `status`) VALUES
	(1, 'Interface can implement an interface?', '2014-01-09 10:31:17', 1, 1, 0, 0),
	(2, 'Abstract can implement an interface?', '2014-01-09 10:31:17', 1, 1, 0, 0),
	(3, 'An class can implement how many interface?', '2014-01-09 10:31:17', 2, 1, 0, 1),
	(4, 'Match the questions with the answers', '2014-01-09 22:39:24', 6, 1, 0, 0),
	(5, 'Who are you waiting for?', '2014-01-09 22:40:19', 6, 1, 4, 0),
	(6, 'What happened to Bob?', '2014-01-09 22:43:35', 6, 1, 4, 0),
	(7, 'How long does it take you to get there?', '2014-01-09 22:44:40', 6, 1, 4, 0),
	(8, 'Can you tell me the time?', '2014-01-09 22:45:45', 6, 1, 4, 0),
	(57, 'List prime numbers from 1 to 100', '2014-02-15 00:45:34', 5, 2, 0, 1),
	(58, 'How many reasons in your country?', '2014-02-15 00:48:03', 4, 2, 0, 2);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;


-- Dumping structure for table evoter.question_type
CREATE TABLE IF NOT EXISTS `question_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_type_value` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.question_type: ~6 rows (approximately)
DELETE FROM `question_type`;
/*!40000 ALTER TABLE `question_type` DISABLE KEYS */;
INSERT INTO `question_type` (`id`, `question_type_value`) VALUES
	(1, 'Yes/No\r\n'),
	(2, 'Multi Radio Button'),
	(3, 'Multi Checkbox'),
	(4, 'Slider'),
	(5, 'Input Answer'),
	(6, 'Match');
/*!40000 ALTER TABLE `question_type` ENABLE KEYS */;


-- Dumping structure for table evoter.session
CREATE TABLE IF NOT EXISTS `session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subject_id` int(10) unsigned NOT NULL,
  `name` char(100) NOT NULL,
  `creation_date` datetime NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `session_FKIndex1` (`subject_id`),
  KEY `FK_session_user` (`user_id`),
  CONSTRAINT `FK_session_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_session_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.session: ~10 rows (approximately)
DELETE FROM `session`;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` (`id`, `subject_id`, `name`, `creation_date`, `is_active`, `user_id`) VALUES
	(1, 1, 'subject_1_session_1', '2013-12-28 00:00:00', 1, 1),
	(2, 1, 'subject_1_session_2', '2013-12-28 12:50:24', 0, 1),
	(3, 1, 'subject_1_session_3', '2012-12-28 12:50:24', 0, 1),
	(4, 2, 'subject_2_session_4\r\n', '2013-12-28 12:50:24', 0, 2),
	(5, 2, 'subject_2_session_5', '2013-12-28 12:50:24', 0, 2),
	(6, 2, 'subject_2_session_6', '2013-12-28 12:50:24', 0, 2),
	(7, 3, 'subject_3_session_7', '2013-12-28 12:50:24', 0, 1),
	(8, 3, 'subject_3_session_8', '2013-12-28 12:50:24', 0, 1),
	(9, 3, 'subject_3_session_9', '2013-12-28 12:50:24', 0, 1),
	(13, 1, 'subject_1_session_test', '2014-02-15 01:00:00', 0, 1);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


-- Dumping structure for table evoter.session_question
CREATE TABLE IF NOT EXISTS `session_question` (
  `question_id` int(10) unsigned NOT NULL,
  `session_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`question_id`,`session_id`),
  KEY `FK_session_question_session` (`session_id`),
  CONSTRAINT `FK_session_question_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_session_question_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.session_question: ~4 rows (approximately)
DELETE FROM `session_question`;
/*!40000 ALTER TABLE `session_question` DISABLE KEYS */;
INSERT INTO `session_question` (`question_id`, `session_id`) VALUES
	(1, 1),
	(3, 1),
	(4, 1),
	(2, 4);
/*!40000 ALTER TABLE `session_question` ENABLE KEYS */;


-- Dumping structure for table evoter.session_user
CREATE TABLE IF NOT EXISTS `session_user` (
  `user_id` int(10) unsigned NOT NULL,
  `session_id` int(10) unsigned NOT NULL,
  `delete_indicator` tinyint(1) unsigned zerofill NOT NULL,
  `accept_stt` tinyint(1) unsigned zerofill NOT NULL,
  PRIMARY KEY (`user_id`,`session_id`),
  KEY `session_user_FKIndex1` (`session_id`),
  KEY `session_user_FKIndex2` (`user_id`),
  CONSTRAINT `FK_session_user_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_session_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.session_user: ~11 rows (approximately)
DELETE FROM `session_user`;
/*!40000 ALTER TABLE `session_user` DISABLE KEYS */;
INSERT INTO `session_user` (`user_id`, `session_id`, `delete_indicator`, `accept_stt`) VALUES
	(1, 1, 0, 1),
	(1, 13, 0, 1),
	(2, 2, 0, 1),
	(3, 1, 0, 0),
	(3, 2, 1, 1),
	(3, 5, 0, 0),
	(3, 13, 0, 0),
	(4, 1, 0, 1),
	(4, 2, 0, 1),
	(4, 13, 0, 0),
	(23, 4, 0, 0);
/*!40000 ALTER TABLE `session_user` ENABLE KEYS */;


-- Dumping structure for table evoter.statistics
CREATE TABLE IF NOT EXISTS `statistics` (
  `statistics_value` char(255) DEFAULT NULL,
  `answer_id` int(10) unsigned NOT NULL
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

-- Dumping data for table evoter.subject: ~13 rows (approximately)
DELETE FROM `subject`;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` (`id`, `title`, `creation_date`) VALUES
	(1, 'Object Oriented Programming', '2013-12-28 12:50:24'),
	(2, 'Testing Metrics', '2013-12-28 12:50:24'),
	(3, 'Software Engineering', '2013-12-28 12:50:24'),
	(6, 'test subject lalala', '2013-12-31 00:00:00'),
	(7, 'test subject lalala', '2013-12-31 00:00:00'),
	(8, 'test subject lalala', '2013-12-31 00:00:00'),
	(12, 'test subject lalala', '2013-12-31 00:00:00'),
	(13, 'test subject lalala', '2013-12-31 00:00:00'),
	(14, 'test subject lalala', '2013-12-31 00:00:00'),
	(16, 'test subject lalala', '2013-12-31 00:00:00'),
	(18, 'test subject lalala', '2013-12-31 00:00:00'),
	(20, 'test subject lalala', '2013-12-31 00:00:00'),
	(21, 'create a new subject 4 testing %2B edit testing', '2014-12-28 12:50:24');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;


-- Dumping structure for table evoter.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_id` int(10) unsigned NOT NULL,
  `full_name` char(100) DEFAULT NULL,
  `email_address` char(100) NOT NULL,
  `username` char(30) NOT NULL,
  `passwd` char(30) NOT NULL,
  `approved` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_FKIndex1` (`user_type_id`),
  CONSTRAINT `FK_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user: ~8 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_type_id`, `full_name`, `email_address`, `username`, `passwd`, `approved`) VALUES
	(1, 2, 'Paul Gibson', 'paul.gibson@telecom-sudparis.eu', 'paul_gibson', '12345678', 1),
	(2, 2, 'Jean Luc', 'jean.luc@telecom-sudparis.eu', 'jean', '12345678', 1),
	(3, 3, '', 'nguyen.van@telecom-sudparis.eu', 'nvluong', '12345678', 1),
	(4, 3, '', 'nguyen.mai@telecom-sudparis.eu', 'ntmai', '12345678', 1),
	(21, 3, '', 'diem.bt@telecom-sudparis.eu', 'btdiem', '1234567890', 1),
	(22, 2, 'Tuan', 'tuan@yahoo.com', 'tuan', '123456789', 0),
	(23, 1, '', 'someone@gmail.com', 'so', '98adsajdsa', 0),
	(28, 3, '', 'diemth@gmail.com', 'diembt12', 'tuilaai', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table evoter.user_subject
CREATE TABLE IF NOT EXISTS `user_subject` (
  `subject_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`subject_id`,`user_id`),
  KEY `user_subject_FKIndex1` (`subject_id`),
  KEY `FK_user_subject_user` (`user_id`),
  CONSTRAINT `FK_user_subject_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_subject_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user_subject: ~12 rows (approximately)
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
	(3, 4),
	(21, 2),
	(21, 22),
	(21, 23);
/*!40000 ALTER TABLE `user_subject` ENABLE KEYS */;


-- Dumping structure for table evoter.user_type
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_type_value` char(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- Dumping data for table evoter.user_type: ~13 rows (approximately)
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
