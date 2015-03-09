CREATE DATABASE  IF NOT EXISTS `cubbyhole` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `cubbyhole`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: cubbyhole
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `stockageclient`
--

DROP TABLE IF EXISTS `stockageclient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stockageclient` (
  `StockageClientID` int(11) NOT NULL AUTO_INCREMENT,
  `ClientID` int(11) NOT NULL,
  `StockageID` int(11) NOT NULL,
  `PermissionID` int(11) NOT NULL,
  `Auteur` tinyint(4) NOT NULL,
  `PartagerID` int(11) DEFAULT NULL,
  PRIMARY KEY (`StockageClientID`),
  KEY `stockageclient_ibfk_1` (`ClientID`),
  KEY `stockageclient_ibfk_2` (`StockageID`),
  KEY `stockageclient_ibfk_3` (`PermissionID`),
  KEY `FK_h39q1q8n9yatxuuie3ptnpviw` (`PartagerID`),
  CONSTRAINT `FK_h39q1q8n9yatxuuie3ptnpviw` FOREIGN KEY (`PartagerID`) REFERENCES `client` (`ClientID`),
  CONSTRAINT `stockageclient_ibfk_1` FOREIGN KEY (`ClientID`) REFERENCES `client` (`ClientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stockageclient_ibfk_2` FOREIGN KEY (`StockageID`) REFERENCES `stockage` (`StockageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stockageclient_ibfk_3` FOREIGN KEY (`PermissionID`) REFERENCES `permission` (`PermissionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stockageclient`
--

LOCK TABLES `stockageclient` WRITE;
/*!40000 ALTER TABLE `stockageclient` DISABLE KEYS */;
INSERT INTO `stockageclient` VALUES (130,1,70,4,1,1),(131,3,71,4,1,3),(132,3,71,4,0,1),(133,1,70,4,0,3),(134,5,72,4,1,5),(135,5,72,4,0,1);
/*!40000 ALTER TABLE `stockageclient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-16 17:25:06
