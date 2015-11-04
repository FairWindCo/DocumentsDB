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
	(1, '2015-10-30 00:00:00', 'trytrytr try trwy twr trw w ', 'trytr', '2015-10-22 00:00:00', '2015-10-06 00:00:00', 1, 2);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.CONTACT_TYPES: ~3 rows (приблизно)
/*!40000 ALTER TABLE `CONTACT_TYPES` DISABLE KEYS */;
INSERT INTO `CONTACT_TYPES` (`id`, `name`, `numberFormat`, `version`) VALUES
	(1, 'e-mail', 'wqwedwq', 0),
	(2, 'телефон', 'wdw', 0),
	(3, 'адрес', '', 0);
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
  `number` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `documentType_ID` bigint(20) DEFAULT NULL,
  `agreement_ID` bigint(20) DEFAULT NULL,
  `security_model` int(11) DEFAULT NULL,
  `person_ID` bigint(20) DEFAULT NULL,
  `counterparty_ID` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `created_user_id` bigint(20) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7dwik5irx2a7qakwjsyagsy20` (`agreement_ID`),
  KEY `FK_1x6wma4o912pgklqodxd03td6` (`created_user_id`),
  KEY `FK_k821q7c3jqqd49duqnx2uf3qq` (`documentType_ID`),
  KEY `FK_ogk4dt5yus0dyorl2ualhgdh5` (`modify_user_id`),
  KEY `FK_1peyp1wwyo9kk8og8sunuuxwx` (`parent_id`),
  KEY `FK_limr04uapwn4wd5bfpj8vsbhl` (`counterparty_ID`),
  KEY `FK_89jsaxmrdyrnmtdnsi6y3hyd7` (`person_ID`),
  CONSTRAINT `FK_1peyp1wwyo9kk8og8sunuuxwx` FOREIGN KEY (`parent_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_1x6wma4o912pgklqodxd03td6` FOREIGN KEY (`created_user_id`) REFERENCES `USERS` (`USER_ID`),
  CONSTRAINT `FK_7dwik5irx2a7qakwjsyagsy20` FOREIGN KEY (`agreement_ID`) REFERENCES `AGREEMENTS` (`id`),
  CONSTRAINT `FK_89jsaxmrdyrnmtdnsi6y3hyd7` FOREIGN KEY (`person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_k821q7c3jqqd49duqnx2uf3qq` FOREIGN KEY (`documentType_ID`) REFERENCES `DOCUMENT_TYPES` (`id`),
  CONSTRAINT `FK_limr04uapwn4wd5bfpj8vsbhl` FOREIGN KEY (`counterparty_ID`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_ogk4dt5yus0dyorl2ualhgdh5` FOREIGN KEY (`modify_user_id`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS: ~1 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS` DISABLE KEYS */;
INSERT INTO `DOCUMENTS` (`id`, `number`, `name`, `description`, `documentType_ID`, `agreement_ID`, `security_model`, `person_ID`, `counterparty_ID`, `parent_id`, `created_user_id`, `creationDate`, `modify_user_id`, `modificationDate`, `version`) VALUES
	(1, '34543', 'dsfdsfds', 'ntcn', 1, 1, NULL, 6, 1, NULL, 4, '2015-10-20 13:18:03', NULL, NULL, 2);
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

-- Dumping data for table favorit.DOCUMENTS_FILES: ~1 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS_FILES` DISABLE KEYS */;
INSERT INTO `DOCUMENTS_FILES` (`DOCUMENTS_id`, `documentFiles_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `DOCUMENTS_FILES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENTS_SECURITY
DROP TABLE IF EXISTS `DOCUMENTS_SECURITY`;
CREATE TABLE IF NOT EXISTS `DOCUMENTS_SECURITY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` int(11) DEFAULT NULL,
  `permission` int(11) DEFAULT NULL,
  `document_id` bigint(20) DEFAULT NULL,
  `person_ID` bigint(20) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lk75bkjtcex6pkst4i1tem79i` (`document_id`),
  KEY `FK_roc3uuthjf6nbte6b3ttf94p3` (`person_ID`),
  CONSTRAINT `FK_lk75bkjtcex6pkst4i1tem79i` FOREIGN KEY (`document_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_roc3uuthjf6nbte6b3ttf94p3` FOREIGN KEY (`person_ID`) REFERENCES `PERSONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS_SECURITY: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS_SECURITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENTS_SECURITY` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENTS_SUBSCRIBE
DROP TABLE IF EXISTS `DOCUMENTS_SUBSCRIBE`;
CREATE TABLE IF NOT EXISTS `DOCUMENTS_SUBSCRIBE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subscribed` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `subscribed_document_ID` bigint(20) DEFAULT NULL,
  `person_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_843h0tgsiuqlnluybxowemp9y` (`subscribed_document_ID`),
  KEY `FK_qecih35e8k2m9wqjsnqaehcep` (`person_ID`),
  CONSTRAINT `FK_843h0tgsiuqlnluybxowemp9y` FOREIGN KEY (`subscribed_document_ID`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_qecih35e8k2m9wqjsnqaehcep` FOREIGN KEY (`person_ID`) REFERENCES `PERSONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENTS_SUBSCRIBE: ~0 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENTS_SUBSCRIBE` DISABLE KEYS */;
/*!40000 ALTER TABLE `DOCUMENTS_SUBSCRIBE` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.DOCUMENT_TYPES
DROP TABLE IF EXISTS `DOCUMENT_TYPES`;
CREATE TABLE IF NOT EXISTS `DOCUMENT_TYPES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `counter` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `numberFormat` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `documentClass` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.DOCUMENT_TYPES: ~7 rows (приблизно)
/*!40000 ALTER TABLE `DOCUMENT_TYPES` DISABLE KEYS */;
INSERT INTO `DOCUMENT_TYPES` (`id`, `counter`, `name`, `numberFormat`, `version`, `documentClass`) VALUES
	(1, 0, 'Входящая корреспонденция', 'ВХ-№%1/%4', 0, 1),
	(2, 0, 'Исходящая кореспонденция', NULL, 0, 2),
	(3, 0, 'Счета на оплату', NULL, 0, 1),
	(4, 0, 'Конструкторская документация', NULL, 0, 0),
	(5, 0, 'Электронные схемы', NULL, 0, 0),
	(6, 0, 'Руководства по эксплуатации', NULL, 0, 0),
	(7, 0, 'Нормативная документация (ГОСТ/ДСТУ/ОСТ и др)', NULL, 0, 0);
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

-- Dumping data for table favorit.EXECUTORS: ~2 rows (приблизно)
/*!40000 ALTER TABLE `EXECUTORS` DISABLE KEYS */;
INSERT INTO `EXECUTORS` (`task_id`, `person_id`) VALUES
	(1, 3),
	(1, 4);
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
  `document_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f2bay5ofmwqh0rupxb8phtob7` (`document_id`),
  CONSTRAINT `FK_f2bay5ofmwqh0rupxb8phtob7` FOREIGN KEY (`document_id`) REFERENCES `DOCUMENTS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.FILES: ~2 rows (приблизно)
/*!40000 ALTER TABLE `FILES` DISABLE KEYS */;
INSERT INTO `FILES` (`id`, `creationDate`, `fileName`, `filePath`, `mimeType`, `size`, `document_id`) VALUES
	(1, '2015-10-20 16:35:00', 'file', 'Входящая корреспонденция', 'image/jpeg', 27667, 1),
	(2, '2015-10-22 11:54:03', '20_37.jpg', 'E:/DOCUMENTS/Входящая корреспонденция/dsfdsfds/20_37.jpg', 'image/jpeg', 27667, 1);
/*!40000 ALTER TABLE `FILES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.MESSAGES
DROP TABLE IF EXISTS `MESSAGES`;
CREATE TABLE IF NOT EXISTS `MESSAGES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actual` datetime DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `messageText` varchar(255) DEFAULT NULL,
  `created_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_24dkexdrbbc339dsf37g77nfn` (`created_user_id`),
  CONSTRAINT `FK_24dkexdrbbc339dsf37g77nfn` FOREIGN KEY (`created_user_id`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.MESSAGES: ~2 rows (приблизно)
/*!40000 ALTER TABLE `MESSAGES` DISABLE KEYS */;
INSERT INTO `MESSAGES` (`id`, `actual`, `creationDate`, `messageText`, `created_user_id`) VALUES
	(1, NULL, '2015-11-02 11:36:09', 'Запуск новой системы', 3),
	(2, '2015-11-11 00:00:00', '2015-11-02 14:58:38', '<span style="font-weight: 700;"><u>Тестирование системы</u></span><p>Начни с начала.</p>', 4);
/*!40000 ALTER TABLE `MESSAGES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.MESSAGES_RECIPIENT
DROP TABLE IF EXISTS `MESSAGES_RECIPIENT`;
CREATE TABLE IF NOT EXISTS `MESSAGES_RECIPIENT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `validationDate` datetime DEFAULT NULL,
  `message_ID` bigint(20) DEFAULT NULL,
  `person_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3o6ftkkbbqycss7elh7r8bqsc` (`message_ID`),
  KEY `FK_13vl1ccqh4lfqt6y7bkbea1i3` (`person_ID`),
  CONSTRAINT `FK_13vl1ccqh4lfqt6y7bkbea1i3` FOREIGN KEY (`person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_3o6ftkkbbqycss7elh7r8bqsc` FOREIGN KEY (`message_ID`) REFERENCES `MESSAGES` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.MESSAGES_RECIPIENT: ~6 rows (приблизно)
/*!40000 ALTER TABLE `MESSAGES_RECIPIENT` DISABLE KEYS */;
INSERT INTO `MESSAGES_RECIPIENT` (`id`, `validationDate`, `message_ID`, `person_ID`) VALUES
	(2, NULL, 1, 3),
	(3, NULL, 1, 2),
	(4, NULL, 1, 5),
	(5, '2015-11-03 08:47:28', 1, 4),
	(6, '2015-11-03 15:48:29', 2, 4),
	(7, NULL, 2, 3);
/*!40000 ALTER TABLE `MESSAGES_RECIPIENT` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE
DROP TABLE IF EXISTS `NOMENCLATURE`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `combined` bit(1) NOT NULL,
  `version` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE: ~8 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE` DISABLE KEYS */;
INSERT INTO `NOMENCLATURE` (`id`, `code`, `country`, `manufacturer`, `name`, `combined`, `version`, `description`) VALUES
	(1, 'DP5G', 'USA', 'Amptek', 'Спектрометр DP5G', b'0', 0, ''),
	(2, 'TRG10R050', 'Китай', 'CINCON ELECTRONICS CO.,LTD', 'Блок питания к спектрометру DP5G', b'0', 0, 'В проектах не используетсься'),
	(3, 'DP5', 'USA', 'Amptek', 'Спектрометр DP5', b'1', 1, NULL),
	(4, NULL, 'Китай', 'CINCON ELECTRONICS CO.,LTD', 'Блок питания к спектрометру DP5', b'0', 0, 'В проектах не используетсься'),
	(5, 'IBPM', 'Украина', 'ДП "Фаворит плюс"', 'Аэрозольный монитор IBPM', b'1', 1, 'ФП.33.00.00.00.СБ'),
	(6, 'SDDR3_13', NULL, NULL, 'Память опративная SO-DIMM DDR3 1.3V', b'0', 0, NULL),
	(7, 'SDDR3_15', NULL, NULL, 'Память опративная SO-DIMM DDR3 1.5V', b'0', 0, NULL),
	(8, 'SDDR2', NULL, NULL, 'Память опративная SO-DIMM DDR2', b'0', 0, NULL);
/*!40000 ALTER TABLE `NOMENCLATURE` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE_NOMENCLATURE_TYPES
DROP TABLE IF EXISTS `NOMENCLATURE_NOMENCLATURE_TYPES`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE_NOMENCLATURE_TYPES` (
  `NOMENCLATURE_id` bigint(20) NOT NULL,
  `nomenclatureTypes_id` bigint(20) NOT NULL,
  PRIMARY KEY (`NOMENCLATURE_id`,`nomenclatureTypes_id`),
  KEY `FK_tnei3w5f9qxt1q6fvsrolvpuf` (`nomenclatureTypes_id`),
  CONSTRAINT `FK_32t734d05gl4ibwt5nb11xo5h` FOREIGN KEY (`NOMENCLATURE_id`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_tnei3w5f9qxt1q6fvsrolvpuf` FOREIGN KEY (`nomenclatureTypes_id`) REFERENCES `NOMENCLATURE_TYPES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE_NOMENCLATURE_TYPES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE_NOMENCLATURE_TYPES` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOMENCLATURE_NOMENCLATURE_TYPES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE_SAFEKEEPING
DROP TABLE IF EXISTS `NOMENCLATURE_SAFEKEEPING`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE_SAFEKEEPING` (
  `NOMENCLATURE_id` bigint(20) NOT NULL,
  `safekeeping_id` bigint(20) NOT NULL,
  PRIMARY KEY (`NOMENCLATURE_id`,`safekeeping_id`),
  UNIQUE KEY `UK_fpqb9mtdbryw5fn9o7c3j5pnq` (`safekeeping_id`),
  CONSTRAINT `FK_fpqb9mtdbryw5fn9o7c3j5pnq` FOREIGN KEY (`safekeeping_id`) REFERENCES `SAFEKEEPING` (`id`),
  CONSTRAINT `FK_i14oragxsxwugjmu77jdhpb92` FOREIGN KEY (`NOMENCLATURE_id`) REFERENCES `NOMENCLATURE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE_SAFEKEEPING: ~0 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE_SAFEKEEPING` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOMENCLATURE_SAFEKEEPING` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE_TAMPLATES
DROP TABLE IF EXISTS `NOMENCLATURE_TAMPLATES`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE_TAMPLATES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_nomenclature_id` bigint(20) DEFAULT NULL,
  `nomenclature_id` bigint(20) DEFAULT NULL,
  `nomenclature_type_id` bigint(20) DEFAULT NULL,
  `units` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ls6w4ltxvhihl3ij630be3ny9` (`parent_id`),
  KEY `FK_jhaokbslocilc5vx7clgadxsa` (`parent_nomenclature_id`),
  KEY `FK_j8eipymwysdwy9k5ug548altr` (`nomenclature_id`),
  KEY `FK_2mxe2c68a6t15jcl35s06cg6p` (`nomenclature_type_id`),
  CONSTRAINT `FK_2mxe2c68a6t15jcl35s06cg6p` FOREIGN KEY (`nomenclature_type_id`) REFERENCES `NOMENCLATURE_TYPES` (`id`),
  CONSTRAINT `FK_j8eipymwysdwy9k5ug548altr` FOREIGN KEY (`nomenclature_id`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_jhaokbslocilc5vx7clgadxsa` FOREIGN KEY (`parent_nomenclature_id`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_ls6w4ltxvhihl3ij630be3ny9` FOREIGN KEY (`parent_id`) REFERENCES `NOMENCLATURE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE_TAMPLATES: ~1 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE_TAMPLATES` DISABLE KEYS */;
INSERT INTO `NOMENCLATURE_TAMPLATES` (`id`, `count`, `version`, `parent_id`, `parent_nomenclature_id`, `nomenclature_id`, `nomenclature_type_id`, `units`) VALUES
	(6, 2, 0, NULL, 5, 3, NULL, NULL);
/*!40000 ALTER TABLE `NOMENCLATURE_TAMPLATES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE_TYPES
DROP TABLE IF EXISTS `NOMENCLATURE_TYPES`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE_TYPES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE_TYPES: ~0 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE_TYPES` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOMENCLATURE_TYPES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.NOMENCLATURE_TYPES_NOMENCLATURE
DROP TABLE IF EXISTS `NOMENCLATURE_TYPES_NOMENCLATURE`;
CREATE TABLE IF NOT EXISTS `NOMENCLATURE_TYPES_NOMENCLATURE` (
  `NOMENCLATURE_TYPES_id` bigint(20) NOT NULL,
  `nomenclature_id` bigint(20) NOT NULL,
  PRIMARY KEY (`NOMENCLATURE_TYPES_id`,`nomenclature_id`),
  KEY `FK_oettxvv0hrbtxay1lj8g7h2jx` (`nomenclature_id`),
  CONSTRAINT `FK_8240p2betiqygx2og86xvbivr` FOREIGN KEY (`NOMENCLATURE_TYPES_id`) REFERENCES `NOMENCLATURE_TYPES` (`id`),
  CONSTRAINT `FK_oettxvv0hrbtxay1lj8g7h2jx` FOREIGN KEY (`nomenclature_id`) REFERENCES `NOMENCLATURE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.NOMENCLATURE_TYPES_NOMENCLATURE: ~0 rows (приблизно)
/*!40000 ALTER TABLE `NOMENCLATURE_TYPES_NOMENCLATURE` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOMENCLATURE_TYPES_NOMENCLATURE` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.PERSONS: ~13 rows (приблизно)
/*!40000 ALTER TABLE `PERSONS` DISABLE KEYS */;
INSERT INTO `PERSONS` (`id`, `comments`, `date_of_birth`, `first_name`, `middle_name`, `surname`, `version`, `counterparty_id`, `head_of_id`, `position_id`) VALUES
	(1, NULL, NULL, 'Лариса', 'Ивановна', 'Бернадина', 0, 1, NULL, 1),
	(2, NULL, NULL, 'Сергей', 'Владимирович', 'Недеря', 0, 1, 1, 2),
	(3, NULL, NULL, 'Владимир', 'Михайлович', 'Коротенко', 0, 1, 2, 3),
	(4, NULL, NULL, 'Сергей', 'Юрьевич', 'Маненок', 0, 1, 2, NULL),
	(5, NULL, NULL, 'Николай', 'Владимирович', 'Стрильчук', 0, 1, 3, 7),
	(6, NULL, NULL, 'Григорий', 'Андреевич', 'Поляк', 0, 1, 5, 8),
	(7, NULL, NULL, 'Александр', 'Петрович', 'Кочетов', 0, 1, 5, 9),
	(8, NULL, NULL, 'Алексей', 'Александрович', 'Кочетов', 0, 1, NULL, NULL),
	(9, NULL, NULL, 'Вятчеслва', 'Дмитриевич', 'Поляков', 0, 1, NULL, NULL),
	(10, NULL, NULL, 'Дмитрий', 'Вятчеславович', 'Поляков', 0, 1, NULL, NULL),
	(11, NULL, NULL, 'Василий', 'Петрович', 'Гудемчюк', 0, 1, NULL, NULL),
	(12, NULL, NULL, 'Анатолий', '', 'Куценко', 0, 1, NULL, NULL),
	(13, NULL, NULL, 'Александр', NULL, 'Марущенко', 0, 1, NULL, NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.POSITIONS: ~13 rows (приблизно)
/*!40000 ALTER TABLE `POSITIONS` DISABLE KEYS */;
INSERT INTO `POSITIONS` (`id`, `name`, `version`) VALUES
	(1, 'Директор', 0),
	(2, 'Технический директор', 0),
	(3, 'Начальник НЦТ (Научно-Технического Центра)', 0),
	(4, 'ИТ инженер', 0),
	(5, 'Офис менеджер', 0),
	(6, 'Констркутор', 0),
	(7, 'Главный инженер', 0),
	(8, 'Начальник проектно-конструкторского отдела', 0),
	(9, 'Начальник производственного отдела', 0),
	(10, 'Начальник отдела технического контроля', 0),
	(11, 'Кладовщик', 0),
	(12, 'Сервис инженер', 0),
	(13, 'Начальник участка технческой документации', 0);
/*!40000 ALTER TABLE `POSITIONS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.PRODUCTS
DROP TABLE IF EXISTS `PRODUCTS`;
CREATE TABLE IF NOT EXISTS `PRODUCTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `guaranteePeriod` datetime DEFAULT NULL,
  `self_created` bit(1) NOT NULL,
  `serial_number` varchar(255) DEFAULT NULL,
  `shippingDate` datetime DEFAULT NULL,
  `composed_ID` bigint(20) DEFAULT NULL,
  `nomenclature_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_trfkenr0hcrgl9ujdpmw31b6d` (`composed_ID`),
  KEY `FK_8tb4wcfuvdygah5d71biexjog` (`nomenclature_ID`),
  CONSTRAINT `FK_8tb4wcfuvdygah5d71biexjog` FOREIGN KEY (`nomenclature_ID`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_trfkenr0hcrgl9ujdpmw31b6d` FOREIGN KEY (`composed_ID`) REFERENCES `PRODUCTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.PRODUCTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `PRODUCTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.PRODUCTS_DOCUMENTS
DROP TABLE IF EXISTS `PRODUCTS_DOCUMENTS`;
CREATE TABLE IF NOT EXISTS `PRODUCTS_DOCUMENTS` (
  `PRODUCTS_id` bigint(20) NOT NULL,
  `productDocuments_id` bigint(20) NOT NULL,
  PRIMARY KEY (`PRODUCTS_id`,`productDocuments_id`),
  UNIQUE KEY `UK_qkcfciyeuib66oyn3ukjwqkbn` (`productDocuments_id`),
  CONSTRAINT `FK_cp0lpmtjgdibtgyyk298h5678` FOREIGN KEY (`PRODUCTS_id`) REFERENCES `PRODUCTS` (`id`),
  CONSTRAINT `FK_qkcfciyeuib66oyn3ukjwqkbn` FOREIGN KEY (`productDocuments_id`) REFERENCES `DOCUMENTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.PRODUCTS_DOCUMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `PRODUCTS_DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRODUCTS_DOCUMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.RepairIteration
DROP TABLE IF EXISTS `RepairIteration`;
CREATE TABLE IF NOT EXISTS `RepairIteration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `repair_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t908e5slojqgy4eu6t71rhg6w` (`repair_ID`),
  CONSTRAINT `FK_t908e5slojqgy4eu6t71rhg6w` FOREIGN KEY (`repair_ID`) REFERENCES `REPAIRS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.RepairIteration: ~0 rows (приблизно)
/*!40000 ALTER TABLE `RepairIteration` DISABLE KEYS */;
/*!40000 ALTER TABLE `RepairIteration` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.REPAIRS
DROP TABLE IF EXISTS `REPAIRS`;
CREATE TABLE IF NOT EXISTS `REPAIRS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `lastStatusChange` datetime DEFAULT NULL,
  `repairResult` varchar(255) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `product_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gvlteb6odhk0jmyekjgvtfsov` (`product_ID`),
  CONSTRAINT `FK_gvlteb6odhk0jmyekjgvtfsov` FOREIGN KEY (`product_ID`) REFERENCES `PRODUCTS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.REPAIRS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `REPAIRS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REPAIRS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.REQUESTS
DROP TABLE IF EXISTS `REQUESTS`;
CREATE TABLE IF NOT EXISTS `REQUESTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `approvedDate` datetime DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `executed` bit(1) NOT NULL,
  `operationDate` datetime DEFAULT NULL,
  `typeRequest` int(11) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `agreement_ID` bigint(20) DEFAULT NULL,
  `approved_person_ID` bigint(20) DEFAULT NULL,
  `counterparty_id` bigint(20) DEFAULT NULL,
  `parent_request_ID` bigint(20) DEFAULT NULL,
  `responsible_person_ID` bigint(20) DEFAULT NULL,
  `executedDate` datetime DEFAULT NULL,
  `executed_person_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oce0k03fl0ahjmsg6xlxb08mb` (`agreement_ID`),
  KEY `FK_lpitk89d0cr1h200ro5bjw8rd` (`approved_person_ID`),
  KEY `FK_1bfckrsfpb3qw9phfovysod9` (`counterparty_id`),
  KEY `FK_axxkk2jgi3xle3sbx4n4bt7kd` (`parent_request_ID`),
  KEY `FK_3p6f98e6drxjxuvmkyjklexhe` (`responsible_person_ID`),
  KEY `FK_enrk09bd7dceoievmt0924h3y` (`executed_person_ID`),
  CONSTRAINT `FK_1bfckrsfpb3qw9phfovysod9` FOREIGN KEY (`counterparty_id`) REFERENCES `Counterparty` (`id`),
  CONSTRAINT `FK_3p6f98e6drxjxuvmkyjklexhe` FOREIGN KEY (`responsible_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_axxkk2jgi3xle3sbx4n4bt7kd` FOREIGN KEY (`parent_request_ID`) REFERENCES `REQUESTS` (`id`),
  CONSTRAINT `FK_enrk09bd7dceoievmt0924h3y` FOREIGN KEY (`executed_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_lpitk89d0cr1h200ro5bjw8rd` FOREIGN KEY (`approved_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_oce0k03fl0ahjmsg6xlxb08mb` FOREIGN KEY (`agreement_ID`) REFERENCES `AGREEMENTS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.REQUESTS: ~5 rows (приблизно)
/*!40000 ALTER TABLE `REQUESTS` DISABLE KEYS */;
INSERT INTO `REQUESTS` (`id`, `approvedDate`, `comments`, `executed`, `operationDate`, `typeRequest`, `version`, `agreement_ID`, `approved_person_ID`, `counterparty_id`, `parent_request_ID`, `responsible_person_ID`, `executedDate`, `executed_person_ID`) VALUES
	(1, NULL, 'dfbgfdgbfd', b'0', '2015-10-29 15:44:11', 0, 0, NULL, NULL, 2, NULL, 4, NULL, NULL),
	(2, NULL, 'fcwewe', b'0', '2015-10-29 16:40:02', 0, 2, 1, NULL, 2, NULL, 4, NULL, NULL),
	(3, NULL, 'fddfgdf', b'0', '2015-10-29 16:01:09', 0, 0, 1, NULL, 2, NULL, 4, NULL, NULL),
	(4, NULL, 'разработка прототипа', b'0', '2015-10-30 08:16:39', 0, 0, 1, NULL, 2, NULL, 2, NULL, NULL),
	(5, '2015-10-30 11:04:31', 'wwef', b'0', '2015-10-30 08:20:25', 2, 1, 1, 4, 2, NULL, 2, NULL, NULL);
/*!40000 ALTER TABLE `REQUESTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.REQUEST_ITEMS
DROP TABLE IF EXISTS `REQUEST_ITEMS`;
CREATE TABLE IF NOT EXISTS `REQUEST_ITEMS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `count` bigint(20) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `units` int(11) DEFAULT NULL,
  `nomenclature_ID` bigint(20) DEFAULT NULL,
  `request_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jbfxctdwykidihx3rqx3pf771` (`nomenclature_ID`),
  KEY `FK_9jhkdmtk4c031yeatvrxl144x` (`request_ID`),
  CONSTRAINT `FK_9jhkdmtk4c031yeatvrxl144x` FOREIGN KEY (`request_ID`) REFERENCES `REQUESTS` (`id`),
  CONSTRAINT `FK_jbfxctdwykidihx3rqx3pf771` FOREIGN KEY (`nomenclature_ID`) REFERENCES `NOMENCLATURE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.REQUEST_ITEMS: ~5 rows (приблизно)
/*!40000 ALTER TABLE `REQUEST_ITEMS` DISABLE KEYS */;
INSERT INTO `REQUEST_ITEMS` (`id`, `comments`, `count`, `lastUpdate`, `units`, `nomenclature_ID`, `request_ID`) VALUES
	(1, 'wdfdsf', 234, '2015-10-30 14:03:30', 1, 4, 2),
	(2, '23432', 2, '2015-10-30 14:13:31', 3, 1, 1),
	(3, 'dfq', 3, '2015-10-30 14:13:47', 2, 8, 3),
	(4, 'werrwe', 1, '2015-10-30 14:14:27', 5, 3, 5),
	(5, NULL, 3, '2015-10-30 14:14:41', 4, 3, 4);
/*!40000 ALTER TABLE `REQUEST_ITEMS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.SAFEKEEPING
DROP TABLE IF EXISTS `SAFEKEEPING`;
CREATE TABLE IF NOT EXISTS `SAFEKEEPING` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` bigint(20) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `units` varchar(255) DEFAULT NULL,
  `nomenclature_ID` bigint(20) DEFAULT NULL,
  `storehouse_ID` bigint(20) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `defective` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oh4qm7t7kr0aifo1fwo642749` (`nomenclature_ID`),
  KEY `FK_rwfutr8tjpm5pc8a5f3lebgrs` (`storehouse_ID`),
  CONSTRAINT `FK_oh4qm7t7kr0aifo1fwo642749` FOREIGN KEY (`nomenclature_ID`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_rwfutr8tjpm5pc8a5f3lebgrs` FOREIGN KEY (`storehouse_ID`) REFERENCES `STOREHOUSES` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.SAFEKEEPING: ~1 rows (приблизно)
/*!40000 ALTER TABLE `SAFEKEEPING` DISABLE KEYS */;
INSERT INTO `SAFEKEEPING` (`id`, `count`, `lastUpdate`, `units`, `nomenclature_ID`, `storehouse_ID`, `comments`, `defective`) VALUES
	(1, 10, '2015-10-22 15:57:54', NULL, 8, 1, NULL, b'0');
/*!40000 ALTER TABLE `SAFEKEEPING` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.STOREHOUSES
DROP TABLE IF EXISTS `STOREHOUSES`;
CREATE TABLE IF NOT EXISTS `STOREHOUSES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `responsible_person_ID` bigint(20) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kr8kiqylaesuo24t7uog4c1wn` (`responsible_person_ID`),
  CONSTRAINT `FK_kr8kiqylaesuo24t7uog4c1wn` FOREIGN KEY (`responsible_person_ID`) REFERENCES `PERSONS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.STOREHOUSES: ~4 rows (приблизно)
/*!40000 ALTER TABLE `STOREHOUSES` DISABLE KEYS */;
INSERT INTO `STOREHOUSES` (`id`, `comments`, `location`, `name`, `number`, `responsible_person_ID`, `version`) VALUES
	(1, 'Участок подготовки компьютерного оборудования', 'Комната 36', 'Участок програмирования', 'Р36', 4, 0),
	(2, 'Склад', 'Комната 2', 'Склад', 'Склад', NULL, 0),
	(3, 'Сервісна дільниця', 'Комната 37', 'Сервісна дільниця', 'C37', NULL, 0),
	(4, NULL, 'Комната 37', 'Дільниця крупно-вузлового складання ', 'D37', NULL, 0);
/*!40000 ALTER TABLE `STOREHOUSES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.STOREHOUSES_SAFEKEEPING
DROP TABLE IF EXISTS `STOREHOUSES_SAFEKEEPING`;
CREATE TABLE IF NOT EXISTS `STOREHOUSES_SAFEKEEPING` (
  `STOREHOUSES_id` bigint(20) NOT NULL,
  `safekeeping_id` bigint(20) NOT NULL,
  PRIMARY KEY (`STOREHOUSES_id`,`safekeeping_id`),
  UNIQUE KEY `UK_gobu2jpwlpp6ntdprl558jhbr` (`safekeeping_id`),
  CONSTRAINT `FK_gobu2jpwlpp6ntdprl558jhbr` FOREIGN KEY (`safekeeping_id`) REFERENCES `SAFEKEEPING` (`id`),
  CONSTRAINT `FK_s56ts6tp8tsbuah6ql25lr9ay` FOREIGN KEY (`STOREHOUSES_id`) REFERENCES `STOREHOUSES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.STOREHOUSES_SAFEKEEPING: ~0 rows (приблизно)
/*!40000 ALTER TABLE `STOREHOUSES_SAFEKEEPING` DISABLE KEYS */;
/*!40000 ALTER TABLE `STOREHOUSES_SAFEKEEPING` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.STOREHOUSE_MOVEMENT
DROP TABLE IF EXISTS `STOREHOUSE_MOVEMENT`;
CREATE TABLE IF NOT EXISTS `STOREHOUSE_MOVEMENT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operationDate` datetime DEFAULT NULL,
  `typeMovement` int(11) DEFAULT NULL,
  `counterparty_id` bigint(20) DEFAULT NULL,
  `from_storehouse_ID` bigint(20) DEFAULT NULL,
  `responsible_person_ID` bigint(20) DEFAULT NULL,
  `to_storehouse_ID` bigint(20) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `approvedDate` datetime DEFAULT NULL,
  `approved_person_ID` bigint(20) DEFAULT NULL,
  `acknowledgementDate` datetime DEFAULT NULL,
  `requestNumber` varchar(255) DEFAULT NULL,
  `acknowledgement_person_ID` bigint(20) DEFAULT NULL,
  `request_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cyptvs5urycc421jqcogu4qlb` (`counterparty_id`),
  KEY `FK_cs9r5jao91y2tty0xkfcox59p` (`from_storehouse_ID`),
  KEY `FK_caq6tuljghn8b9rmrqus9561i` (`responsible_person_ID`),
  KEY `FK_c4hxr9qw79g9lanof45yiaxe9` (`to_storehouse_ID`),
  KEY `FK_6vtwllotl9edya7fcvphfrr8k` (`approved_person_ID`),
  KEY `FK_8xn0350joypnncepj8jjfuivs` (`acknowledgement_person_ID`),
  KEY `FK_39jvvr6vjh0khd0n7sw2yvx9p` (`request_ID`),
  CONSTRAINT `FK_39jvvr6vjh0khd0n7sw2yvx9p` FOREIGN KEY (`request_ID`) REFERENCES `REQUESTS` (`id`),
  CONSTRAINT `FK_6vtwllotl9edya7fcvphfrr8k` FOREIGN KEY (`approved_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_8xn0350joypnncepj8jjfuivs` FOREIGN KEY (`acknowledgement_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_c4hxr9qw79g9lanof45yiaxe9` FOREIGN KEY (`to_storehouse_ID`) REFERENCES `STOREHOUSES` (`id`),
  CONSTRAINT `FK_caq6tuljghn8b9rmrqus9561i` FOREIGN KEY (`responsible_person_ID`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_cs9r5jao91y2tty0xkfcox59p` FOREIGN KEY (`from_storehouse_ID`) REFERENCES `STOREHOUSES` (`id`),
  CONSTRAINT `FK_cyptvs5urycc421jqcogu4qlb` FOREIGN KEY (`counterparty_id`) REFERENCES `Counterparty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.STOREHOUSE_MOVEMENT: ~2 rows (приблизно)
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT` DISABLE KEYS */;
INSERT INTO `STOREHOUSE_MOVEMENT` (`id`, `operationDate`, `typeMovement`, `counterparty_id`, `from_storehouse_ID`, `responsible_person_ID`, `to_storehouse_ID`, `comments`, `approvedDate`, `approved_person_ID`, `acknowledgementDate`, `requestNumber`, `acknowledgement_person_ID`, `request_ID`) VALUES
	(1, '2015-10-26 15:00:02', 0, 2, NULL, 4, 3, 'R142', NULL, NULL, NULL, NULL, NULL, NULL),
	(2, '2015-10-27 09:09:28', 0, 2, NULL, 4, 2, 'На рмент', NULL, NULL, NULL, 'S23423', NULL, NULL);
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.STOREHOUSE_MOVEMENT_DOCUMENTS
DROP TABLE IF EXISTS `STOREHOUSE_MOVEMENT_DOCUMENTS`;
CREATE TABLE IF NOT EXISTS `STOREHOUSE_MOVEMENT_DOCUMENTS` (
  `STOREHOUSE_MOVEMENT_id` bigint(20) NOT NULL,
  `documents_id` bigint(20) NOT NULL,
  PRIMARY KEY (`STOREHOUSE_MOVEMENT_id`,`documents_id`),
  UNIQUE KEY `UK_2yrdltwsgmpxclr1ri406n8b` (`documents_id`),
  CONSTRAINT `FK_2yrdltwsgmpxclr1ri406n8b` FOREIGN KEY (`documents_id`) REFERENCES `DOCUMENTS` (`id`),
  CONSTRAINT `FK_od1yay90xi8roq2vhc37bget8` FOREIGN KEY (`STOREHOUSE_MOVEMENT_id`) REFERENCES `STOREHOUSE_MOVEMENT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.STOREHOUSE_MOVEMENT_DOCUMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT_DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT_DOCUMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.STOREHOUSE_MOVEMENT_ELEMENTS
DROP TABLE IF EXISTS `STOREHOUSE_MOVEMENT_ELEMENTS`;
CREATE TABLE IF NOT EXISTS `STOREHOUSE_MOVEMENT_ELEMENTS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `count` bigint(20) NOT NULL,
  `created` bit(1) NOT NULL,
  `serialNumber` varchar(255) DEFAULT NULL,
  `units` int(11) DEFAULT NULL,
  `movement_ID` bigint(20) DEFAULT NULL,
  `nomenclature_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ds6aw2f3ia391yes9jscdihvm` (`movement_ID`),
  KEY `FK_be4udmp1opm6i1sxangvgjgwu` (`nomenclature_ID`),
  CONSTRAINT `FK_be4udmp1opm6i1sxangvgjgwu` FOREIGN KEY (`nomenclature_ID`) REFERENCES `NOMENCLATURE` (`id`),
  CONSTRAINT `FK_ds6aw2f3ia391yes9jscdihvm` FOREIGN KEY (`movement_ID`) REFERENCES `STOREHOUSE_MOVEMENT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.STOREHOUSE_MOVEMENT_ELEMENTS: ~0 rows (приблизно)
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT_ELEMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `STOREHOUSE_MOVEMENT_ELEMENTS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.TASKS
DROP TABLE IF EXISTS `TASKS`;
CREATE TABLE IF NOT EXISTS `TASKS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `dedLineDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `task_type_id` bigint(20) DEFAULT NULL,
  `responsible_person_id` bigint(20) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `endControlDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fx8qq2mtbrifqkim6pr38y1li` (`create_user_id`),
  KEY `FK_gbt7kwa814huysysgpy8otb8y` (`responsible_person_id`),
  KEY `FK_geq0ybfepta8w7oar08ug5dob` (`task_type_id`),
  CONSTRAINT `FK_fx8qq2mtbrifqkim6pr38y1li` FOREIGN KEY (`create_user_id`) REFERENCES `USERS` (`USER_ID`),
  CONSTRAINT `FK_gbt7kwa814huysysgpy8otb8y` FOREIGN KEY (`responsible_person_id`) REFERENCES `PERSONS` (`id`),
  CONSTRAINT `FK_geq0ybfepta8w7oar08ug5dob` FOREIGN KEY (`task_type_id`) REFERENCES `TASK_TYPES` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.TASKS: ~2 rows (приблизно)
/*!40000 ALTER TABLE `TASKS` DISABLE KEYS */;
INSERT INTO `TASKS` (`id`, `description`, `creationDate`, `dedLineDate`, `startDate`, `version`, `task_type_id`, `responsible_person_id`, `result`, `endDate`, `create_user_id`, `modificationDate`, `endControlDate`) VALUES
	(1, '<h2><b><i>tryttwr</i></b></h2><p>trywtwt</p><p>wrywtwr</p>', '2015-11-03 11:32:37', '2015-11-30 00:00:00', '2015-11-01 00:00:00', 7, 5, 3, NULL, NULL, 4, '2015-11-03 14:57:35', NULL),
	(2, '<strong>ewrewewrew</strong>ewrewwer<h1>Your title here...</h1>', '2015-11-03 11:56:59', '2015-11-03 14:55:37', '2015-11-03 14:55:35', 1, 5, 4, NULL, NULL, 4, '2015-11-03 14:50:23', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.TASK_TYPES: ~5 rows (приблизно)
/*!40000 ALTER TABLE `TASK_TYPES` DISABLE KEYS */;
INSERT INTO `TASK_TYPES` (`id`, `name`, `version`) VALUES
	(1, 'Разработка документации', 0),
	(2, 'Производство изделий', 1),
	(3, 'Ремонт оборудования', 1),
	(4, 'Модификация технической документации', 0),
	(5, 'Поручения', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USERS: ~4 rows (приблизно)
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` (`USER_ID`, `ENABLED`, `PASSWORD`, `USERNAME`, `versionId`, `person_id`) VALUES
	(1, b'1', '123', 'adminus', 0, 4),
	(2, b'1', 'volmiko', 'volmiko', 0, 3),
	(3, b'1', 'manenok', 'manenok', 2, 4),
	(4, b'1', 'pass', 'admin', 6, 4);
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.USERS_USER_ROLES
DROP TABLE IF EXISTS `USERS_USER_ROLES`;
CREATE TABLE IF NOT EXISTS `USERS_USER_ROLES` (
  `USERS_USER_ID` bigint(20) NOT NULL,
  `userRoles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`USERS_USER_ID`,`userRoles_id`),
  KEY `FK_44vw6pqy62euw2g6y3f60pym3` (`userRoles_id`),
  CONSTRAINT `FK_44vw6pqy62euw2g6y3f60pym3` FOREIGN KEY (`userRoles_id`) REFERENCES `USER_ROLES` (`id`),
  CONSTRAINT `FK_kf2rdklx3gojnnhpig08yun6t` FOREIGN KEY (`USERS_USER_ID`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USERS_USER_ROLES: ~14 rows (приблизно)
/*!40000 ALTER TABLE `USERS_USER_ROLES` DISABLE KEYS */;
INSERT INTO `USERS_USER_ROLES` (`USERS_USER_ID`, `userRoles_id`) VALUES
	(4, 1),
	(3, 2),
	(2, 3),
	(3, 3),
	(4, 3),
	(3, 4),
	(4, 6),
	(4, 8),
	(4, 9),
	(4, 10),
	(4, 14),
	(4, 16),
	(4, 17),
	(4, 20);
/*!40000 ALTER TABLE `USERS_USER_ROLES` ENABLE KEYS */;


-- Dumping structure for таблиця favorit.USER_ROLES
DROP TABLE IF EXISTS `USER_ROLES`;
CREATE TABLE IF NOT EXISTS `USER_ROLES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- Dumping data for table favorit.USER_ROLES: ~32 rows (приблизно)
/*!40000 ALTER TABLE `USER_ROLES` DISABLE KEYS */;
INSERT INTO `USER_ROLES` (`id`, `description`, `name`, `version`) VALUES
	(1, 'Управление пользователями', 'ROLE_ADMIN', 0),
	(2, 'Просмотр документов', 'ROLE_VIEW_DOCUMENTS', 0),
	(3, 'Изменение документов', 'ROLE_EDIT_DOCUMENTS', 0),
	(4, 'Создание документов', 'ROLE_ADD_DOCUMENTS', 0),
	(5, 'Удаление докуентов', 'ROLE_DELETE_DOCUMENTS', 0),
	(6, 'Редактирование справочников', 'ROLE_DIRECTORY', 0),
	(7, 'Создание задач', 'ROLE_TASK_CREATE', 0),
	(8, 'Управление складом', 'ROLE_STOREHOUSE', 0),
	(9, 'Управление всеми кладами', 'ROLE_STOREHOUSE_MASTER', 0),
	(10, 'Просмотр состояние складов', 'ROLE_STOREHOUSE_VIEW', 0),
	(11, 'Создание информации об изделиях', 'ROLE_PRODUCT_ADD', 0),
	(12, 'Создание информации о ремонтах', 'ROLE_REPAIR_ADD', 0),
	(13, 'Изменение информации о ремонте', 'ROLE_REPAIR_MANAGE', 0),
	(14, 'Просмотр информации о продуктах', 'ROLE_PRODUCT_VIEW', 0),
	(15, 'Отправка сообщений', 'ROLE_MESSAGE_ADD', 0),
	(16, 'Право просмотра запросов', 'ROLE_REQUEST_VIEW', 0),
	(17, 'Право создания и редактирования запросов', 'ROLE_REQUEST_EDIT', 0),
	(18, 'Право утверждения запросов на покупку', 'ROLE_SUBSCRIBE_REQUEST_PURCHASE', 0),
	(19, 'Право утверждения запросов на отгрузку', 'ROLE_SUBSCRIBE_REQUEST_SHIPMENT', 0),
	(20, 'Право утверждения запросов на производство', 'ROLE_SUBSCRIBE_REQUEST_PRODUCTION', 0),
	(21, 'Право утверждения запросов на ремонт', 'ROLE_SUBSCRIBE_REQUEST_REPAIR', 0),
	(22, 'Право утверждения получения на склад', 'ROLE_SUBSCRIBE_STOREHOUSE_ARRIVAL', 0),
	(23, 'Право утверждения отгрузки со склада', 'ROLE_SUBSCRIBE_STOREHOUSE_SHIPMENT', 0),
	(24, 'Право утверждения передачи между складами', 'ROLE_SUBSCRIBE_STOREHOUSE_MOVE', 0),
	(25, 'Право утверждения производства', 'ROLE_SUBSCRIBE_STOREHOUSE_COMBINED', 0),
	(26, 'Право утверждения утилизации', 'ROLE_SUBSCRIBE_STOREHOUSE_UTILIZATION', 0),
	(27, 'Право утверждения забраковки', 'ROLE_SUBSCRIBE_STOREHOUSE_DEFECTIVE', 0),
	(28, 'Право утверждения документов', 'ROLE_SUBSCRIBE_DOCUMENT', 0),
	(29, 'Право завершения запросов по складу', 'ROLE_COMMIT_REQUEST_STOREHOUSE', 0),
	(30, 'Право завершения запросов по ремонту', 'ROLE_COMMIT_REQUEST_REPAIR', 0),
	(31, 'Право завершения запросов по производству', 'ROLE_COMMIT_REQUEST_PRODACTION', 0),
	(32, 'Право управление и редактирование всеми сообщениями', 'ROLE_MESSAGE_ADMINISTRATOR', 0);
/*!40000 ALTER TABLE `USER_ROLES` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
