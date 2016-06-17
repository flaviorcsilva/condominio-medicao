-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: condominio
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `leitura`
--

DROP TABLE IF EXISTS `leitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leitura` (
  `id_leitura` bigint(11) NOT NULL AUTO_INCREMENT,
  `dt_leitura` tinyblob NOT NULL,
  `nu_medidor` varchar(20) NOT NULL,
  `vl_medido` int(11) NOT NULL,
  PRIMARY KEY (`id_leitura`),
  KEY `NU_MEDIDOR_idx` (`nu_medidor`),
  CONSTRAINT `FK_MEDIDOR` FOREIGN KEY (`nu_medidor`) REFERENCES `medidor` (`nu_medidor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leitura`
--

LOCK TABLES `leitura` WRITE;
/*!40000 ALTER TABLE `leitura` DISABLE KEYS */;
INSERT INTO `leitura` VALUES (11,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-212',14389),(12,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-212',7607),(13,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-212',9100),(14,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-211',4096),(15,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-211',79),(16,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-211',210),(17,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-213',10615),(18,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-213',4559),(19,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-213',25990),(20,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-214',15977),(21,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-214',3940),(22,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-214',22380),(23,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-211',4487),(24,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-211',269),(25,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-211',210),(26,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-212',15179),(27,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-212',7981),(28,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-212',9100),(29,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-213',11856),(30,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-213',5183),(31,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-213',28975),(32,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGF-214',17500),(33,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PAGQ-214',4373),(34,'¬\í\0sr\0\rjava.time.Ser•]„º\"H²\0\0xpw\0\0\àx','PGAS-214',24248);
/*!40000 ALTER TABLE `leitura` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-17 18:17:39
