-- --------------------------------------------------------
-- Сервер:                       127.0.0.1
-- Версія сервера:               5.5.41-log - MySQL Community Server (GPL)
-- ОС сервера:                   Win32
-- HeidiSQL Версія:              9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for favorit
DROP DATABASE IF EXISTS `favorit`;
CREATE DATABASE IF NOT EXISTS `favorit` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `favorit`;


-- Dumping structure for таблиця favorit.AGREEMENTS
DROP TABLE IF EXISTS `AGREEMENTS`;
CREATE TABLE IF NOT EXISTS `AGREEMENTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `planEndDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `counterparty_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aydm2g8nrhv3pdedfbp12ud20` (`counterparty_id`),
  CONSTRAINT `FK_aydm2g8nrhv3pdedfbp12ud20` FOREIGN KEY (`counterparty_id`) REFERENCES `Counterparty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.AGREEMENTS: ~1 rows (приблизно)
/*!40000 ALTER TABLE `AGREEMENTS` DISABLE KEYS */;
INSERT INTO `AGREEMENTS` (`id`, `endDate`, `name`, `number`, `planEndDate`, `startDate`, `version`, `counterparty_id`) VALUES
	(1, '2015-10-30 00:00:00', 'trytrytr try trwy twr trw w ', 'trytr', '2015-10-22 00:00:00', '2015-10-06 00:00:00', 1, NULL);
/*!40000 ALTER TABLE `AGREEMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.CONTACTS
DROP TABLE IF EXISTS `CONTACTS`;
CREATE TABLE IF NOT EXISTS `CONTACTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `contact_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_908yghar9p1jlix6rgvs81r42` (`contact_type_id`),
  CONSTRAINT `FK_908yghar9p1jlix6rgvs81r42` FOREIGN KEY (`contact_type_id`) REFERENCES `CONTACT_TYPES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.CONTACTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `CONTACTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CONTACTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.CONTACT_TYPES
DROP TABLE IF EXISTS `CONTACT_TYPES`;
CREATE TABLE IF NOT EXISTS `CONTACT_TYPES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `numberFormat` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.CONTACT_TYPES: ~12 rows (приблизно)
/*!40000 ALTER TABLE `CONTACT_TYPES` DISABLE KEYS */;
INSERT INTO `CONTACT_TYPES` (`id`, `name`, `numberFormat`, `version`) VALUES
	(1, 'wqdq', 'wqwedwq', 0),
	(2, 'wdwe', 'wdw', 0),
	(3, 'test', 'ewtdwd', 0),
	(4, 'test2', 'efwwe', 0),
	(5, 'dsfds', 'sdfdsf', 0),
	(6, 'qw23', '12', 1),
	(7, 'dfddsfds', 'dsfadsfads', 0),
	(8, 'ewfewfweweew', 'ewfewfewwfweew', 1),
	(9, 'wefwe', 'ewfew', 0),
	(10, 'efew', 'ewfe', 0),
	(11, 'wefew', 'werf', 0),
	(12, 'teste_ADD', 'sacSA', 0);
/*!40000 ALTER TABLE `CONTACT_TYPES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.Counterparty
DROP TABLE IF EXISTS `Counterparty`;
CREATE TABLE IF NOT EXISTS `Counterparty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.Counterparty: ~2 rows (приблизно)
/*!40000 ALTER TABLE `Counterparty` DISABLE KEYS */;
INSERT INTO `Counterparty` (`id`, `full_name`, `short_name`, `version`) VALUES
	(1, 'Дочернее Предприятие ....', 'ДП "Фаворит-Плюс"', 0),
	(2, 'ОП "ЮАЭС"', 'ЮАЭС', 0);
/*!40000 ALTER TABLE `Counterparty` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.Counterparty_CONTACTS
DROP TABLE IF EXISTS `Counterparty_CONTACTS`;
CREATE TABLE IF NOT EXISTS `Counterparty_CONTACTS` (
  `Counterparty_id` bigint(20) NOT NULL,
  `contacts_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Counterparty_id`,`contacts_id`),
  UNIQUE KEY `UK_1hbywaexcxhdfa9mbc6xxy3ep` (`contacts_id`),
  CONSTRAINT `FK_1hbywaexcxhdfa9mbc6xxy3ep` FOREIGN KEY (`contacts_id`) REFERENCES `CONTACTS` (`id`),
  CONSTRAINT `FK_trjgsolify6gfdhxu37guk1rr` FOREIGN KEY (`Counterparty_id`) REFERENCES `Counterparty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.Counterparty_CONTACTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `Counterparty_CONTACTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `Counterparty_CONTACTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENTS
DROP TABLE IF EXISTS `DOCUMENTS`;
CREATE TABLE IF NOT EXISTS `DOCUMENTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `agreement_ID` bigint(20) DEFAULT NULL,
  `counterparty_from_ID` bigint(20) DEFAULT NULL,
  `counterparty_to_ID` bigint(20) DEFAULT NULL,
  `created_user_id` bigint(20) DEFAULT NULL,
  `documentType_ID` bigint(20) DEFAULT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `person_from_ID` bigint(20) DEFAULT NULL,
  `person_to_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7dwik5irx2a7qakwjsyagsy20` (`agreement_ID`),
  KEY `FK_fwe2rrdjni6wnu0o8bn4u417t` (`counterparty_from_ID`),
  KEY `FK_kl33s0xsb5j2crm2u2v7suyjs` (`counterparty_to_ID`),
  KEY `FK_1x6wma4o912pgklqodxd03td6` (`created_user_id`),
  KEY `FK_k821q7c3jqqd49duqnx2uf3qq` (`documentType_ID`),
  KEY `FK_ogk4dt5yus0dyorl2ualhgdh5` (`modify_user_id`),
  KEY `FK_1peyp1wwyo9kk8og8sunuuxwx` (`parent_id`),
  KEY `FK_nl6q1ui9fgxfkq2nhqfntsa93` (`person_from_ID`),
  KEY `FK_14j3raoo1353p9anlyboqx2p9` (`person_to_ID`),
  CONSTRAINT `FK_14j3raoo1353p9anlyboqx2p9` FOREIGN KEY (`person_to_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_1peyp1wwyo9kk8og8sunuuxwx` FOREIGN KEY (`parent_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_1x6wma4o912pgklqodxd03td6` FOREIGN KEY (`created_user_id`) REFERENCES `USERS` (`USER_ID`),
  CONSTRAINT `FK_7dwik5irx2a7qakwjsyagsy20` FOREIGN KEY (`agreement_ID`) REFERENCES `AGREEMENTS` (`id`),
  CONSTRAINT `FK_fwe2rrdjni6wnu0o8bn4u417t` FOREIGN KEY (`counterparty_from_ID`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_k821q7c3jqqd49duqnx2uf3qq` FOREIGN KEY (`documentType_ID`) REFERENCES `DOCUMENT_TYPES` (`id`),
  CONSTRAINT `FK_kl33s0xsb5j2crm2u2v7suyjs` FOREIGN KEY (`counterparty_to_ID`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_nl6q1ui9fgxfkq2nhqfntsa93` FOREIGN KEY (`person_from_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_ogk4dt5yus0dyorl2ualhgdh5` FOREIGN KEY (`modify_user_id`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENTS_DOCUMENTS
DROP TABLE IF EXISTS `DOCUMENTS_DOCUMENTS`;
CREATE TABLE IF NOT EXISTS `DOCUMENTS_DOCUMENTS` (
  `DOCUMENTS_id` bigint(20) NOT NULL,
  `atachments_id` bigint(20) NOT NULL,
  PRIMARY KEY (`DOCUMENTS_id`,`atachments_id`),
  UNIQUE KEY `UK_jow52oopn57moxbj9kld5xm8v` (`atachments_id`),
  CONSTRAINT `FK_b6s5hi32bj11ccwfufgb326bb` FOREIGN KEY (`DOCUMENTS_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_jow52oopn57moxbj9kld5xm8v` FOREIGN KEY (`atachments_id`) REFERENCES `DOCUMENTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS_DOCUMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS_DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENTS_DOCUMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENTS_FILES
DROP TABLE IF EXISTS `DOCUMENTS_FILES`;
CREATE TABLE IF NOT EXISTS `DOCUMENTS_FILES` (
  `DOCUMENTS_id` bigint(20) NOT NULL,
  `documentFiles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`DOCUMENTS_id`,`documentFiles_id`),
  UNIQUE KEY `UK_jvh445y3txpvy56b9ves18f8p` (`documentFiles_id`),
  CONSTRAINT `FK_d32x3wfjjk6gmov77txqsctgd` FOREIGN KEY (`DOCUMENTS_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_jvh445y3txpvy56b9ves18f8p` FOREIGN KEY (`documentFiles_id`) REFERENCES `FILES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS_FILES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS_FILES` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENTS_FILES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENT_TYPES
DROP TABLE IF EXISTS `DOCUMENT_TYPES`;
CREATE TABLE IF NOT EXISTS `DOCUMENT_TYPES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `counter` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `numberFormat` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENT_TYPES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENT_TYPES` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENT_TYPES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.EXECUTORS
DROP TABLE IF EXISTS `EXECUTORS`;
CREATE TABLE IF NOT EXISTS `EXECUTORS` (
  `task_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`person_id`),
  KEY `FK_pbo9qvoj8dh34rp9ppu8r493d` (`person_id`),
  CONSTRAINT `FK_bk2rmref1agfrdwhhg1o66qa3` FOREIGN KEY (`task_id`) REFERENCES `TASKS` (`id`),
  CONSTRAINT `FK_pbo9qvoj8dh34rp9ppu8r493d` FOREIGN KEY (`person_id`) REFERENCES `PERSONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.EXECUTORS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `EXECUTORS` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXECUTORS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.FILES
DROP TABLE IF EXISTS `FILES`;
CREATE TABLE IF NOT EXISTS `FILES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `mimeType` varchar(255) DEFAULT NULL,
  `size` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.FILES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `FILES` DISABLE KEYS */;
/*!40000 ALTER TABLE `FILES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.PERSONS
DROP TABLE IF EXISTS `PERSONS`;
CREATE TABLE IF NOT EXISTS `PERSONS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `counterparty_id` bigint(20) DEFAULT NULL,
  `head_of_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9ds67nmiw9hlmmmtx4tkj5x89` (`counterparty_id`),
  KEY `FK_b4n40d61hy8serxe0bwliy5ao` (`head_of_id`),
  KEY `FK_r1igp630q3rvmkqyixl87tppu` (`position_id`),
  CONSTRAINT `FK_9ds67nmiw9hlmmmtx4tkj5x89` FOREIGN KEY (`counterparty_id`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_b4n40d61hy8serxe0bwliy5ao` FOREIGN KEY (`head_of_id`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_r1igp630q3rvmkqyixl87tppu` FOREIGN KEY (`position_id`) REFERENCES `POSITIONS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.PERSONS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `PERSONS` DISABLE KEYS */;
INSERT INTO `PERSONS` (`id`, `comments`, `date_of_birth`, `first_name`, `middle_name`, `surname`, `version`, `counterparty_id`, `head_of_id`, `position_id`) VALUES
	(1, NULL, NULL, 'Лариса', 'Ивановна', 'Бернадина', 0, 1, NULL, 1),
	(2, NULL, NULL, 'Сергей', 'Владимирович', 'Недеря', 0, 1, 1, 2),
	(3, NULL, NULL, 'Владимир', 'Михайлович', 'Коротенко', 0, 1, 2, 3);
/*!40000 ALTER TABLE `PERSONS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.PERSONS_CONTACTS
DROP TABLE IF EXISTS `PERSONS_CONTACTS`;
CREATE TABLE IF NOT EXISTS `PERSONS_CONTACTS` (
  `PERSONS_id` bigint(20) NOT NULL,
  `contacts_id` bigint(20) NOT NULL,
  PRIMARY KEY (`PERSONS_id`,`contacts_id`),
  UNIQUE KEY `UK_fwi3hf1oe806t77hhlh25yh7d` (`contacts_id`),
  CONSTRAINT `FK_6902fo0nt2ifqh45qp4l4imxr` FOREIGN KEY (`PERSONS_id`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_fwi3hf1oe806t77hhlh25yh7d` FOREIGN KEY (`contacts_id`) REFERENCES `CONTACTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.PERSONS_CONTACTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `PERSONS_CONTACTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `PERSONS_CONTACTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.POSITIONS
DROP TABLE IF EXISTS `POSITIONS`;
CREATE TABLE IF NOT EXISTS `POSITIONS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.POSITIONS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `POSITIONS` DISABLE KEYS */;
INSERT INTO `POSITIONS` (`id`, `name`, `version`) VALUES
	(1, 'Директор', 0),
	(2, 'Технический директор', 0),
	(3, 'Начальник НЦТ (Научно-Технического Центра)', 0);
/*!40000 ALTER TABLE `POSITIONS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.TASKS
DROP TABLE IF EXISTS `TASKS`;
CREATE TABLE IF NOT EXISTS `TASKS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `dedLineDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `counterparty_id` bigint(20) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `responsible_person_id` bigint(20) DEFAULT NULL,
  `task__type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l5k6ychlrsg9av46nr0pv3qo` (`counterparty_id`),
  KEY `FK_fx8qq2mtbrifqkim6pr38y1li` (`create_user_id`),
  KEY `FK_gbt7kwa814huysysgpy8otb8y` (`responsible_person_id`),
  KEY `FK_pe3lksp9s3g8wntb7g5g6vmkt` (`task__type_id`),
  CONSTRAINT `FK_fx8qq2mtbrifqkim6pr38y1li` FOREIGN KEY (`create_user_id`) REFERENCES `USERS` (`USER_ID`),
  CONSTRAINT `FK_gbt7kwa814huysysgpy8otb8y` FOREIGN KEY (`responsible_person_id`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_l5k6ychlrsg9av46nr0pv3qo` FOREIGN KEY (`counterparty_id`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_pe3lksp9s3g8wntb7g5g6vmkt` FOREIGN KEY (`task__type_id`) REFERENCES `TASK_TYPES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.TASKS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `TASKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `TASKS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.TASK_DOCUMENTS
DROP TABLE IF EXISTS `TASK_DOCUMENTS`;
CREATE TABLE IF NOT EXISTS `TASK_DOCUMENTS` (
  `task_id` bigint(20) NOT NULL,
  `document_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`document_id`),
  KEY `FK_j02l6sclbck827uxbb49lookr` (`document_id`),
  CONSTRAINT `FK_5idtsu3kahpuehlu99bm6vueo` FOREIGN KEY (`task_id`) REFERENCES `TASKS` (`id`),
  CONSTRAINT `FK_j02l6sclbck827uxbb49lookr` FOREIGN KEY (`document_id`) REFERENCES `DOCUMENTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.TASK_DOCUMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `TASK_DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `TASK_DOCUMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.TASK_TYPES
DROP TABLE IF EXISTS `TASK_TYPES`;
CREATE TABLE IF NOT EXISTS `TASK_TYPES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.TASK_TYPES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `TASK_TYPES` DISABLE KEYS */;
/*!40000 ALTER TABLE `TASK_TYPES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.USERS
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE IF NOT EXISTS `USERS` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENABLED` bit(1) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) NOT NULL,
  `versionId` bigint(20) NOT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `FK_67vkq98kh9w3v0c1fspdl8700` (`person_id`),
  CONSTRAINT `FK_67vkq98kh9w3v0c1fspdl8700` FOREIGN KEY (`person_id`) REFERENCES `PERSONS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USERS: ~1 rows (приблизно)
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` (`USER_ID`, `ENABLED`, `PASSWORD`, `USERNAME`, `versionId`, `person_id`) VALUES
	(1, b'1', 'ewew', 'yesy', 0, NULL);
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.USERS_USER_ROELS
DROP TABLE IF EXISTS `USERS_USER_ROELS`;
CREATE TABLE IF NOT EXISTS `USERS_USER_ROELS` (
  `USERS_USER_ID` bigint(20) NOT NULL,
  `userRoles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`USERS_USER_ID`,`userRoles_id`),
  KEY `FK_jva5yjq35yxlqkj93s3mpicqr` (`userRoles_id`),
  CONSTRAINT `FK_gedijra6njdpvx7exncw459hn` FOREIGN KEY (`USERS_USER_ID`) REFERENCES `USERS` (`USER_ID`),
  CONSTRAINT `FK_jva5yjq35yxlqkj93s3mpicqr` FOREIGN KEY (`userRoles_id`) REFERENCES `USER_ROELS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USERS_USER_ROELS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `USERS_USER_ROELS` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERS_USER_ROELS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.USER_ROELS
DROP TABLE IF EXISTS `USER_ROELS`;
CREATE TABLE IF NOT EXISTS `USER_ROELS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USER_ROELS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `USER_ROELS` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_ROELS` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
