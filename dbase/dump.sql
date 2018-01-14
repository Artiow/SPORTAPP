CREATE DATABASE  IF NOT EXISTS `sport` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sport`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: sport
-- ------------------------------------------------------
-- Server version	5.6.34

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
-- Table structure for table `Athlete`
--

DROP TABLE IF EXISTS `Athlete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Athlete` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(100) NOT NULL,
  `Birth` date NOT NULL,
  `Category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Athlete`
--

LOCK TABLES `Athlete` WRITE;
/*!40000 ALTER TABLE `Athlete` DISABLE KEYS */;
INSERT INTO `Athlete` VALUES (1,'Иванов Иван Иванович','1994-07-22',NULL),(2,'Спортсменов Владимир Абрамович','1993-09-09',NULL),(3,'Атлетов Василий Михайлович','1999-12-15',NULL),(9,'Белов Всеволод Александрович','1996-04-10',NULL),(10,'Дементьева Алёна Викторовна','1999-01-22',NULL),(11,'Орехова Лилия Игнатьева','2000-06-06',NULL),(12,'Филатов Вениамин Михайлович','1989-05-01',NULL),(14,'Петров Петр Петрович','2018-01-09',NULL);
/*!40000 ALTER TABLE `Athlete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Competition`
--

DROP TABLE IF EXISTS `Competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Competition` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Sport_ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Date` date NOT NULL,
  `Venue` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Competition_Sport1_idx` (`Sport_ID`),
  CONSTRAINT `fk_Competition_Sport1` FOREIGN KEY (`Sport_ID`) REFERENCES `Sport` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Competition`
--

LOCK TABLES `Competition` WRITE;
/*!40000 ALTER TABLE `Competition` DISABLE KEYS */;
INSERT INTO `Competition` VALUES (1,5,'Соревнование по плевкам в потолок','2018-12-27','Здесь и сейчас'),(3,24,'Лесной бегун','2017-04-03','Лес'),(6,17,'Пробежка до ларька','2017-12-27','Местный ларёк'),(7,24,'Большой забег','2016-05-04','Далеко'),(8,24,'Огромный забег','2018-11-15','Дальше');
/*!40000 ALTER TABLE `Competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Participate`
--

DROP TABLE IF EXISTS `Participate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Participate` (
  `Athlete_ID` int(11) NOT NULL,
  `Competition_ID` int(11) NOT NULL,
  `Result` int(11) NOT NULL,
  PRIMARY KEY (`Athlete_ID`,`Competition_ID`),
  KEY `fk_Athlete_has_Competition_Competition1_idx` (`Competition_ID`),
  KEY `fk_Athlete_has_Competition_Athlete1_idx` (`Athlete_ID`),
  CONSTRAINT `fk_Athlete_has_Competition_Athlete1` FOREIGN KEY (`Athlete_ID`) REFERENCES `Athlete` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Athlete_has_Competition_Competition1` FOREIGN KEY (`Competition_ID`) REFERENCES `Competition` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Participate`
--

LOCK TABLES `Participate` WRITE;
/*!40000 ALTER TABLE `Participate` DISABLE KEYS */;
INSERT INTO `Participate` VALUES (1,1,5),(1,3,15),(1,8,12),(2,1,4),(2,3,3),(3,1,7),(3,7,15),(9,6,14),(10,3,9),(10,6,15);
/*!40000 ALTER TABLE `Participate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sport`
--

DROP TABLE IF EXISTS `Sport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sport` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Unit_ID` int(11) NOT NULL,
  `Unit_Reverse` tinyint(1) NOT NULL DEFAULT '0',
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Sport_Unit1_idx` (`Unit_ID`),
  CONSTRAINT `fk_Sport_Unit1` FOREIGN KEY (`Unit_ID`) REFERENCES `Unit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sport`
--

LOCK TABLES `Sport` WRITE;
/*!40000 ALTER TABLE `Sport` DISABLE KEYS */;
INSERT INTO `Sport` VALUES (3,1,0,'Прыжки в высоту'),(4,2,0,'Приседания со штангой'),(5,1,0,'Плевки в потолок'),(17,9,1,'Бег 100 метров'),(24,20,1,'Бег 5 километров');
/*!40000 ALTER TABLE `Sport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SportTeam`
--

DROP TABLE IF EXISTS `SportTeam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SportTeam` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Foundation` date NOT NULL,
  `City` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SportTeam`
--

LOCK TABLES `SportTeam` WRITE;
/*!40000 ALTER TABLE `SportTeam` DISABLE KEYS */;
/*!40000 ALTER TABLE `SportTeam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TeamMembership`
--

DROP TABLE IF EXISTS `TeamMembership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TeamMembership` (
  `Athlete_ID` int(11) NOT NULL,
  `SportTeam_ID` int(11) NOT NULL,
  `sport_ID` int(11) NOT NULL,
  PRIMARY KEY (`Athlete_ID`,`SportTeam_ID`),
  KEY `fk_Athlete_has_SportTeam_SportTeam1_idx` (`SportTeam_ID`),
  KEY `fk_Athlete_has_SportTeam_Athlete_idx` (`Athlete_ID`),
  CONSTRAINT `fk_Athlete_has_SportTeam_Athlete` FOREIGN KEY (`Athlete_ID`) REFERENCES `Athlete` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Athlete_has_SportTeam_SportTeam1` FOREIGN KEY (`SportTeam_ID`) REFERENCES `SportTeam` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TeamMembership`
--

LOCK TABLES `TeamMembership` WRITE;
/*!40000 ALTER TABLE `TeamMembership` DISABLE KEYS */;
/*!40000 ALTER TABLE `TeamMembership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Unit`
--

DROP TABLE IF EXISTS `Unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Unit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Unit`
--

LOCK TABLES `Unit` WRITE;
/*!40000 ALTER TABLE `Unit` DISABLE KEYS */;
INSERT INTO `Unit` VALUES (1,'Метры'),(2,'Килограммы'),(9,'Секунды'),(20,'Минуты'),(21,'Километры');
/*!40000 ALTER TABLE `Unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorldRecord`
--

DROP TABLE IF EXISTS `WorldRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorldRecord` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Sport_ID` int(11) NOT NULL,
  `Value` int(11) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_WorldRecord_Sport1_idx` (`Sport_ID`),
  CONSTRAINT `fk_WorldRecord_Sport1` FOREIGN KEY (`Sport_ID`) REFERENCES `Sport` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorldRecord`
--

LOCK TABLES `WorldRecord` WRITE;
/*!40000 ALTER TABLE `WorldRecord` DISABLE KEYS */;
INSERT INTO `WorldRecord` VALUES (1,5,4,'2017-09-01'),(5,24,10,'2018-01-04');
/*!40000 ALTER TABLE `WorldRecord` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-13 13:39:59
