-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: goldgym
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer` (
  `trainer_id` int NOT NULL AUTO_INCREMENT,
  `memb_id` int NOT NULL,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `gend` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `joining_date` date DEFAULT NULL,
  `salary` int DEFAULT NULL,
  `shift` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`trainer_id`),
  KEY `member_id_idx` (`memb_id`),
  CONSTRAINT `memb_id` FOREIGN KEY (`memb_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer`
--

LOCK TABLES `trainer` WRITE;
/*!40000 ALTER TABLE `trainer` DISABLE KEYS */;
INSERT INTO `trainer` VALUES (1,5,'john','dev','Male','982007654','2023-09-12',20000,'morn'),(2,6,'novak','dan','Male','3455677','2023-08-07',15000,'even'),(3,7,'reena','mishra','Female','56111111','2023-08-16',10000,'morn'),(4,9,'sam','patra','Male','3333333','2023-06-12',25000,'even'),(5,7,'salman','khan','Male','321321245','2023-06-12',30000,'even'),(20,11,'andre','gomes','Male','5555555','2023-09-11',12000,'morn'),(22,10,'dk','bj','Male','4234234535','2023-09-12',33000,'even'),(23,7,'zeba','khan','Female','1111111','2023-08-15',10000,'morn'),(25,7,'salma','gomes','Female','5555555','2023-09-11',13500,'morn'),(28,12,'raju','khan','Male','34876584375','2023-04-07',345345,'morn'),(30,9,'dinesh','sawant','Male','54545436546','2023-09-04',13000,'morn'),(32,5,'chand','shaikh','Male','999999','2023-09-13',2999,'evening'),(39,4,'parthoo','roy','Male','32323232','2023-04-03',2000,'morn'),(40,4,'rostan','lobo','Male','453566','2023-04-05',23444,'morn'),(41,4,'raju','Gujar','Male','324234','2023-10-04',234324,'even');
/*!40000 ALTER TABLE `trainer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-19 10:42:18
