-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.27-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema project
--

CREATE DATABASE IF NOT EXISTS project;
USE project;

--
-- Definition of table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `loginid` varchar(20) NOT NULL DEFAULT '',
  `passwd` varchar(20) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `login`
--

/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`loginid`,`passwd`,`role`) VALUES 
 ('adm','passadm','Admin'),
 ('emp1','abc','Employee'),
 ('emp2','abcd','Employee');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;


--
-- Definition of table `prospect`
--

DROP TABLE IF EXISTS `prospect`;
CREATE TABLE `prospect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(20) DEFAULT NULL,
  `padd` varchar(20) DEFAULT NULL,
  `model` varchar(20) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `empid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `empid` (`empid`),
  CONSTRAINT `prospect_ibfk_1` FOREIGN KEY (`empid`) REFERENCES `login` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prospect`
--

/*!40000 ALTER TABLE `prospect` DISABLE KEYS */;
INSERT INTO `prospect` (`id`,`pname`,`padd`,`model`,`color`,`status`,`phone`,`empid`) VALUES 
 (1,'Raj','Noida','vxi','white','1','7654321','emp1'),
 (2,NULL,NULL,'magna','silver',NULL,'1234567','emp1'),
 (3,'Shivani','Noida','Luxury','Blue','Hot','67458745','emp1'),
 (4,'Ram','Ashok nagar','Asta','Red','Cool','876543','emp2');
/*!40000 ALTER TABLE `prospect` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
