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
-- Table structure for table `medidor`
--

DROP TABLE IF EXISTS `medidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medidor` (
  `nu_medidor` varchar(20) NOT NULL,
  `tp_medidor` int(1) NOT NULL,
  `vl_fator_medidor` int(4) NOT NULL DEFAULT '1',
  `id_unidade` bigint(11) NOT NULL,
  PRIMARY KEY (`nu_medidor`),
  KEY `IDX_FK_UNIDADE_ID_UNIDADE` (`id_unidade`),
  CONSTRAINT `FK_UNIDADE` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medidor`
--

LOCK TABLES `medidor` WRITE;
/*!40000 ALTER TABLE `medidor` DISABLE KEYS */;
INSERT INTO `medidor` VALUES ('PAGF-211',1,100,1),('PAGF-212',1,100,2),('PAGF-213',1,100,3),('PAGF-214',1,100,4),('PAGF-GERAL',1,1,7),('PAGQ-211',2,100,1),('PAGQ-212',2,100,2),('PAGQ-213',2,100,3),('PAGQ-214',2,100,4),('PGAS-211',3,1000,1),('PGAS-212',3,1000,2),('PGAS-213',3,1000,3),('PGAS-214',3,1000,4),('PGAS-GERAL',3,1,7),('RAGF-212',1,1,5),('RAGF-GERAL',1,1,6),('RAGQ-212',2,1,5);
/*!40000 ALTER TABLE `medidor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-17 18:17:38
