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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gend` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `Batch` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (4,'Mihir','2006-06-23','Male','62323424','8 am'),(5,'Seena','2000-04-12','Female','32323232','9 am'),(6,'Aarya','2004-03-14','Male','9820016724','6 pm'),(7,'Vaishali','2005-09-15','Female','645645645','7 pm'),(8,'yash','2004-05-10','Male','99999999','8 pm'),(9,'advay','2023-09-05','Male','911111111','7 am'),(10,'simran','2000-04-23','Female','1212121212','8 am'),(11,'gaurav','2013-08-30','Male','8989898989','8 am'),(12,'avadot','2023-09-11','Male','7365937','8 pm'),(13,'tina','2023-10-17','Female','5435453','9 am'),(14,'reena','2023-10-09','Female','5435435345','6 pm'),(16,'seema dev','2023-09-04','Female','90909090','9 am'),(18,'aarya Khan','2023-10-09','Male','122','7 pm'),(19,'auntu dcosta','1970-10-02','Male','4646464646','8 pm'),(21,'abdul kahliq','2023-10-17','Male','757657','8 am'),(22,'rostan lobo','2023-10-11','Male','3246234','9 am'),(23,'sasha','2002-10-23','Female','112234','8 am'),(24,'yruyrtfu','2023-10-11','Male','ewrfewrw','6 pm'),(25,'ewrtewew','2023-10-11','Female','rtretre','6 pm');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
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
