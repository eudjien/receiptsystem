CREATE DATABASE  IF NOT EXISTS `checksystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `checksystem`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: checksystem
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `_check__check_discount`
--

DROP TABLE IF EXISTS `_check__check_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `_check__check_discount` (
  `check_id` bigint NOT NULL,
  `check_discount_id` bigint NOT NULL,
  PRIMARY KEY (`check_id`,`check_discount_id`),
  KEY `FKnl20k7q5qx1xmxyf8pwlt0mp6` (`check_discount_id`),
  CONSTRAINT `FKni9xx7wpuabibng8il4h1lqus` FOREIGN KEY (`check_id`) REFERENCES `checks` (`id`),
  CONSTRAINT `FKnl20k7q5qx1xmxyf8pwlt0mp6` FOREIGN KEY (`check_discount_id`) REFERENCES `check_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_check__check_discount`
--

LOCK TABLES `_check__check_discount` WRITE;
/*!40000 ALTER TABLE `_check__check_discount` DISABLE KEYS */;
INSERT INTO `_check__check_discount` VALUES (1,15),(16,33),(34,44),(45,61);
/*!40000 ALTER TABLE `_check__check_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `_check_item__check_item_discount`
--

DROP TABLE IF EXISTS `_check_item__check_item_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `_check_item__check_item_discount` (
  `check_item_id` bigint NOT NULL,
  `check_item_discount_id` bigint NOT NULL,
  PRIMARY KEY (`check_item_id`,`check_item_discount_id`),
  KEY `FK2s02ew5xuud4962ie4of71s5y` (`check_item_discount_id`),
  CONSTRAINT `FK2s02ew5xuud4962ie4of71s5y` FOREIGN KEY (`check_item_discount_id`) REFERENCES `check_item_discounts` (`id`),
  CONSTRAINT `FKg64ve0vlad4yje65jhpv8fq0r` FOREIGN KEY (`check_item_id`) REFERENCES `check_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_check_item__check_item_discount`
--

LOCK TABLES `_check_item__check_item_discount` WRITE;
/*!40000 ALTER TABLE `_check_item__check_item_discount` DISABLE KEYS */;
INSERT INTO `_check_item__check_item_discount` VALUES (8,10),(19,20),(24,26),(29,30),(52,53),(57,58);
/*!40000 ALTER TABLE `_check_item__check_item_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_discounts`
--

DROP TABLE IF EXISTS `check_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_discounts` (
  `type` varchar(124) NOT NULL,
  `id` bigint NOT NULL,
  `dependent_discount_id` bigint DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr9bfxvprmgfxddvu51ivcbmyn` (`dependent_discount_id`),
  CONSTRAINT `FKr9bfxvprmgfxddvu51ivcbmyn` FOREIGN KEY (`dependent_discount_id`) REFERENCES `check_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_discounts`
--

LOCK TABLES `check_discounts` WRITE;
/*!40000 ALTER TABLE `check_discounts` DISABLE KEYS */;
INSERT INTO `check_discounts` VALUES ('SimplePercentageCheckDiscount',15,NULL,'-35% на сумму чека'),('SimplePercentageCheckDiscount',33,NULL,'-30% на сумму чека'),('SimplePercentageCheckDiscount',44,NULL,'-20% на сумму чека'),('SimpleConstantCheckDiscount',61,NULL,'-5$ на сумму чека'),('SimpleConstantCheckDiscount',65,NULL,'-10$ на сумму чека'),('SimpleConstantCheckDiscount',66,67,'-10$'),('SimplePercentageCheckDiscount',67,NULL,'-35%'),('SimplePercentageCheckDiscount',68,69,'-35%'),('SimpleConstantCheckDiscount',69,NULL,'-10$'),('SimplePercentageCheckDiscount',70,71,'-35%'),('SimpleConstantCheckDiscount',71,72,'-10$'),('SimplePercentageCheckDiscount',72,NULL,'-15%');
/*!40000 ALTER TABLE `check_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_item_discounts`
--

DROP TABLE IF EXISTS `check_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_item_discounts` (
  `type` varchar(86) NOT NULL,
  `id` bigint NOT NULL,
  `dependent_discount_id` bigint DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqpyo3p1g6hpu5qm6p276648mt` (`dependent_discount_id`),
  CONSTRAINT `FKqpyo3p1g6hpu5qm6p276648mt` FOREIGN KEY (`dependent_discount_id`) REFERENCES `check_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_item_discounts`
--

LOCK TABLES `check_item_discounts` WRITE;
/*!40000 ALTER TABLE `check_item_discounts` DISABLE KEYS */;
INSERT INTO `check_item_discounts` VALUES ('ThresholdPercentageCheckItemDiscount',10,NULL,'-10% если количество продукта больше чем 5'),('ThresholdPercentageCheckItemDiscount',20,21,'Скидка 1% если количество продуктов больше 6'),('SimpleConstantCheckItemDiscount',21,NULL,'Скидка 10 на продукт'),('SimplePercentageCheckItemDiscount',26,NULL,'-30%'),('SimplePercentageCheckItemDiscount',30,NULL,'-40%'),('SimpleConstantCheckItemDiscount',53,NULL,'-5$'),('ThresholdPercentageCheckItemDiscount',58,NULL,'Скидка 10% если количество продуктов больше 2'),('SimplePercentageCheckItemDiscount',73,NULL,'-50%');
/*!40000 ALTER TABLE `check_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_items`
--

DROP TABLE IF EXISTS `check_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_items` (
  `id` bigint NOT NULL,
  `check_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqu2dpj4d6px6a2sm8nb3nny67` (`product_id`,`check_id`),
  KEY `FK6drpcu5rw5qhp4meonaxohhh0` (`check_id`),
  CONSTRAINT `FK6drpcu5rw5qhp4meonaxohhh0` FOREIGN KEY (`check_id`) REFERENCES `checks` (`id`),
  CONSTRAINT `FK8f90481j0tq7slpiwr4yrop1q` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_items`
--

LOCK TABLES `check_items` WRITE;
/*!40000 ALTER TABLE `check_items` DISABLE KEYS */;
INSERT INTO `check_items` VALUES (2,1,3,1),(4,1,5,3),(6,1,7,2),(8,1,9,8),(11,1,12,1),(13,1,14,9),(17,16,14,9),(18,16,12,10),(19,16,9,8),(22,16,23,6),(24,16,25,8),(27,16,28,8),(29,16,7,7),(31,16,32,5),(35,34,9,15),(36,34,3,1),(37,34,38,6),(39,34,40,1),(41,34,42,3),(43,34,28,11),(46,45,47,3),(48,45,32,3),(49,45,7,1),(50,45,51,6),(52,45,25,15),(54,45,23,1),(55,45,56,11),(57,45,14,15),(59,45,60,6);
/*!40000 ALTER TABLE `check_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checks`
--

DROP TABLE IF EXISTS `checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checks` (
  `id` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  `cashier` varchar(255) NOT NULL,
  `date` datetime(6) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj3kjg7c5v4k0ulh718dylvcf2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checks`
--

LOCK TABLES `checks` WRITE;
/*!40000 ALTER TABLE `checks` DISABLE KEYS */;
INSERT INTO `checks` VALUES (1,'ул. Пушкина, д. Калатушкина','Василий Пупкин','2021-03-03 07:10:55.343000','Компьютерный магазин','999 проблем','+375290000000'),(16,'ул. Элементова, д. 1','Екатерина Пупкина','2021-03-03 07:10:55.344000','Гипермаркет','342 элемент','+375290000001'),(34,'ул. Советская, д. 1001','Алексей Пупкин','2021-03-03 07:10:55.344000','Продуктовый магазин','Магазин №1','+375290000002'),(45,'ул. Свиридова, д. 1234','Татьяна Пупкина','2021-03-03 07:10:55.344000','Продуктовый магазин','Магазин №2','+375290000003');
/*!40000 ALTER TABLE `checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emails`
--

DROP TABLE IF EXISTS `emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emails` (
  `id` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2qe2cynxce212q2up2q7i01se` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emails`
--

LOCK TABLES `emails` WRITE;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
INSERT INTO `emails` VALUES (75,'chcksstm1@gmail.com'),(74,'lakadmakatag@gmail.com');
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_emails`
--

DROP TABLE IF EXISTS `event_emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_emails` (
  `id` bigint NOT NULL,
  `email_id` bigint DEFAULT NULL,
  `event_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn481au69osankoe6x0pi1ddfl` (`email_id`,`event_type`),
  CONSTRAINT `FKe0l86d21bmav0lbxpls5jnrti` FOREIGN KEY (`email_id`) REFERENCES `emails` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_emails`
--

LOCK TABLES `event_emails` WRITE;
/*!40000 ALTER TABLE `event_emails` DISABLE KEYS */;
INSERT INTO `event_emails` VALUES (76,74,'PRINT_END'),(77,75,'PRINT_END');
/*!40000 ALTER TABLE `event_emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (78);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo61fmio5yukmmiqgnxf8pnavn` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (3,'Кукуруза',20.00),(5,'Пельмени',12.48),(7,'Макароны',8.50),(9,'Помидоры',3.91),(12,'Картошка',7.50),(14,'Сало',10.00),(23,'Рис',20.00),(25,'Чай',3.51),(28,'Яблоки',8.70),(32,'Апельсины',30.50),(38,'Кофе',3.50),(40,'Колбаса',20.00),(42,'Рыба',50.20),(47,'Монитор',155.50),(51,'HDD',25.50),(56,'Мышка',30.00),(60,'Печенье',2.10),(62,'Конфеты',5.60),(63,'Клавиатура',30.00),(64,'SSD',50.00);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_constant_check_discounts`
--

DROP TABLE IF EXISTS `simple_constant_check_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_constant_check_discounts` (
  `constant` decimal(19,2) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKb2utemq7id9sq4afhb7sbhde6` FOREIGN KEY (`id`) REFERENCES `check_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_constant_check_discounts`
--

LOCK TABLES `simple_constant_check_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_check_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_check_discounts` VALUES (5.00,61),(10.00,65),(10.00,66),(10.00,69),(10.00,71);
/*!40000 ALTER TABLE `simple_constant_check_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_constant_check_item_discounts`
--

DROP TABLE IF EXISTS `simple_constant_check_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_constant_check_item_discounts` (
  `constant` decimal(19,2) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKj0wi0vf11gumtg0gep162scls` FOREIGN KEY (`id`) REFERENCES `check_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_constant_check_item_discounts`
--

LOCK TABLES `simple_constant_check_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_check_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_check_item_discounts` VALUES (10.00,21),(5.00,53);
/*!40000 ALTER TABLE `simple_constant_check_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_percentage_check_discounts`
--

DROP TABLE IF EXISTS `simple_percentage_check_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_percentage_check_discounts` (
  `percent` double NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKmdds7fu2x97rbmi2hio1l8sma` FOREIGN KEY (`id`) REFERENCES `check_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_percentage_check_discounts`
--

LOCK TABLES `simple_percentage_check_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_check_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_check_discounts` VALUES (35,15),(30,33),(20,44),(35,67),(35,68),(35,70),(15,72);
/*!40000 ALTER TABLE `simple_percentage_check_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_percentage_check_item_discounts`
--

DROP TABLE IF EXISTS `simple_percentage_check_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_percentage_check_item_discounts` (
  `percent` double NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK78cuhwasns3aa2jukb3u0k49q` FOREIGN KEY (`id`) REFERENCES `check_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_percentage_check_item_discounts`
--

LOCK TABLES `simple_percentage_check_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_check_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_check_item_discounts` VALUES (30,26),(40,30),(50,73);
/*!40000 ALTER TABLE `simple_percentage_check_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `threshold_percentage_check_item_discounts`
--

DROP TABLE IF EXISTS `threshold_percentage_check_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `threshold_percentage_check_item_discounts` (
  `percent` double NOT NULL,
  `threshold` bigint NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKhfle4t7mnnnmkhkgvgsuqri9q` FOREIGN KEY (`id`) REFERENCES `check_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `threshold_percentage_check_item_discounts`
--

LOCK TABLES `threshold_percentage_check_item_discounts` WRITE;
/*!40000 ALTER TABLE `threshold_percentage_check_item_discounts` DISABLE KEYS */;
INSERT INTO `threshold_percentage_check_item_discounts` VALUES (10,5,10),(1,6,20),(10,2,58);
/*!40000 ALTER TABLE `threshold_percentage_check_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'checksystem'
--

--
-- Dumping routines for database 'checksystem'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-03  7:21:22
