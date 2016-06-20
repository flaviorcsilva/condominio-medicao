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
-- Table structure for table `condominio`
--

DROP TABLE IF EXISTS `condominio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condominio` (
  `cd_condominio` varchar(20) NOT NULL,
  `nm_condominio` varchar(45) NOT NULL,
  `nu_medidor_agua` varchar(20) DEFAULT NULL,
  `nu_medidor_gas` varchar(20) DEFAULT NULL,
  `tk_condominio` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cd_condominio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condominio`
--

LOCK TABLES `condominio` WRITE;
/*!40000 ALTER TABLE `condominio` DISABLE KEYS */;
INSERT INTO `condominio` VALUES ('ile-de-la-cite-resid','Ile de la Cit√© Residencial',NULL,NULL,'ile-de-la-cite'),('privilege-noroeste','Privilege Noroeste','PAGF-GERAL','PGAS-GERAL','privilege');
/*!40000 ALTER TABLE `condominio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumo`
--

DROP TABLE IF EXISTS `consumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumo` (
  `id_consumo` bigint(11) NOT NULL AUTO_INCREMENT,
  `cd_condominio` varchar(20) NOT NULL,
  `dt_consumo` varchar(6) NOT NULL,
  `tp_medicao` int(1) NOT NULL,
  `vl_total_faturado` double NOT NULL,
  `vl_medido_fatura` int(11) NOT NULL,
  PRIMARY KEY (`id_consumo`),
  UNIQUE KEY `UK_CONSUMO` (`cd_condominio`,`dt_consumo`,`tp_medicao`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumo`
--

LOCK TABLES `consumo` WRITE;
/*!40000 ALTER TABLE `consumo` DISABLE KEYS */;
INSERT INTO `consumo` VALUES (1,'privilege-noroeste','201604',1,5849.28,982),(2,'privilege-noroeste','201604',3,1815.03,500),(3,'privilege-noroeste','201605',1,7423.68,1142),(4,'privilege-noroeste','201605',3,1815.03,500),(5,'privilege-noroeste','201606',1,6309.18,1003),(6,'privilege-noroeste','201606',3,1815.03,500);
/*!40000 ALTER TABLE `consumo` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `leitura` VALUES (11,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-212',14389),(12,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-212',7607),(13,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-212',9100),(14,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-211',4096),(15,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-211',79),(16,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-211',210),(17,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-213',10615),(18,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-213',4559),(19,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-213',25990),(20,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-214',15977),(21,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-214',3940),(22,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-214',22380),(23,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-211',4487),(24,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-211',269),(25,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-211',210),(26,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-212',15179),(27,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-212',7981),(28,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-212',9100),(29,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-213',11856),(30,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-213',5183),(31,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-213',28975),(32,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGF-214',17500),(33,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PAGQ-214',4373),(34,'¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‡x','PGAS-214',24248);
/*!40000 ALTER TABLE `leitura` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `medidor` VALUES ('PAGF-211',1,100,1),('PAGF-212',1,100,2),('PAGF-213',1,100,3),('PAGF-214',1,100,4),('PAGQ-211',2,100,1),('PAGQ-212',2,100,2),('PAGQ-213',2,100,3),('PAGQ-214',2,100,4),('PGAS-211',3,1000,1),('PGAS-212',3,1000,2),('PGAS-213',3,1000,3),('PGAS-214',3,1000,4),('RAGF-212',1,1,5),('RAGQ-212',2,1,5);
/*!40000 ALTER TABLE `medidor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade`
--

DROP TABLE IF EXISTS `unidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade` (
  `id_unidade` bigint(11) NOT NULL AUTO_INCREMENT,
  `cd_unidade` varchar(10) NOT NULL,
  `cd_condominio` varchar(20) NOT NULL,
  `ds_unidade` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_unidade`),
  UNIQUE KEY `UK_UNIDADE_CONDOMINIO` (`cd_unidade`,`cd_condominio`),
  KEY `FK_CONDOMINIO_idx` (`cd_condominio`),
  CONSTRAINT `FK_CONDOMINIO` FOREIGN KEY (`cd_condominio`) REFERENCES `condominio` (`cd_condominio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
/*!40000 ALTER TABLE `unidade` DISABLE KEYS */;
INSERT INTO `unidade` VALUES (1,'211','privilege-noroeste','Apartamento 211'),(2,'212','privilege-noroeste','Apartamento 212'),(3,'213','privilege-noroeste','Apartamento 213'),(4,'214','privilege-noroeste','Apartamento 214'),(5,'212','ile-de-la-cite-resid','Apartamento 212'),(8,'211','ile-de-la-cite-resid','Apartamento 211');
/*!40000 ALTER TABLE `unidade` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20 20:50:27
