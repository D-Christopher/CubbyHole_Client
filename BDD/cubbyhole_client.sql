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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `ClientID` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Mdp` varchar(50) NOT NULL,
  `DateOffreDebut` date NOT NULL,
  `DateOffreFin` date NOT NULL,
  `BandePassante` int(11) NOT NULL,
  `Quotas` int(11) NOT NULL,
  `PlanID` int(11) NOT NULL,
  `Espace` int(11) NOT NULL,
  PRIMARY KEY (`ClientID`),
  KEY `client_ibfk_1` (`PlanID`),
  CONSTRAINT `client_ibfk_1` FOREIGN KEY (`PlanID`) REFERENCES `plan` (`PlanID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'DINAUCOURT','Christopher','azerty@azerty','ºåQG5\Zƒ33YrýÑ²','2014-05-28','2014-06-27',0,502,5,68),(2,'La fripouille','Jaquouille','lafripouille@mail.com','ºåQG5\Zƒ33YrýÑ²','2014-05-21','2014-05-21',500,510,3,0),(3,'Bishop','Walter','bibi@mail.com','ºåQG5\Zƒ33YrýÑ²','2014-05-21','2014-05-21',500,506,3,12),(4,'Bonnaud','Eoghann','a@aa','ºåQG5\Zƒ33YrýÑ²','2014-06-06','2014-06-06',500,512,3,0),(5,'test','test','test@test','•n1ÏH¡\":Ùèö«ã¤Ï','2014-06-16','2014-06-16',500,501,3,11),(6,'aaaaa','aaaaa','aaaaa@aaaaa','z.`M\r\0·Ì+ÒCý®Ö ','2014-06-16','2014-06-16',500,512,3,0);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
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
