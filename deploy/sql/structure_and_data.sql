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
-- Table structure for table `_receipt__receipt_discount`
--

DROP TABLE IF EXISTS `_receipt__receipt_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `_receipt__receipt_discount` (
  `receipt_id` bigint NOT NULL,
  `receipt_discount_id` bigint NOT NULL,
  PRIMARY KEY (`receipt_id`,`receipt_discount_id`),
  KEY `FK2tun05jbp5b9h1qtyinjc2jfs` (`receipt_discount_id`),
  CONSTRAINT `FK2tun05jbp5b9h1qtyinjc2jfs` FOREIGN KEY (`receipt_discount_id`) REFERENCES `receipt_discounts` (`id`),
  CONSTRAINT `FKfnt0fyjikxwsa3lidu41k412b` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_receipt__receipt_discount`
--

LOCK TABLES `_receipt__receipt_discount` WRITE;
/*!40000 ALTER TABLE `_receipt__receipt_discount` DISABLE KEYS */;
INSERT INTO `_receipt__receipt_discount` VALUES (1,2),(16,17),(34,35),(45,46);
/*!40000 ALTER TABLE `_receipt__receipt_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `_receipt_item__receipt_item_discount`
--

DROP TABLE IF EXISTS `_receipt_item__receipt_item_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `_receipt_item__receipt_item_discount` (
  `receipt_item_id` bigint NOT NULL,
  `receipt_item_discount_id` bigint NOT NULL,
  PRIMARY KEY (`receipt_item_id`,`receipt_item_discount_id`),
  KEY `FKrkri3fdilypnd5tsqpt9ljwce` (`receipt_item_discount_id`),
  CONSTRAINT `FKenfarm6ytdx191v67pn6b5b1m` FOREIGN KEY (`receipt_item_id`) REFERENCES `receipt_items` (`id`),
  CONSTRAINT `FKrkri3fdilypnd5tsqpt9ljwce` FOREIGN KEY (`receipt_item_discount_id`) REFERENCES `receipt_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_receipt_item__receipt_item_discount`
--

LOCK TABLES `_receipt_item__receipt_item_discount` WRITE;
/*!40000 ALTER TABLE `_receipt_item__receipt_item_discount` DISABLE KEYS */;
INSERT INTO `_receipt_item__receipt_item_discount` VALUES (11,13),(18,20),(21,22),(26,27),(48,49),(50,51);
/*!40000 ALTER TABLE `_receipt_item__receipt_item_discount` ENABLE KEYS */;
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
INSERT INTO `products` VALUES (4,'Пельмени',12.48),(6,'Картошка',7.50),(8,'Кукуруза',20.00),(10,'Сало',10.00),(12,'Помидоры',3.91),(15,'Макароны',8.50),(19,'Чай',3.51),(25,'Рис',20.00),(30,'Апельсины',30.50),(32,'Яблоки',8.70),(39,'Колбаса',20.00),(41,'Кофе',3.50),(43,'Рыба',50.20),(54,'Печенье',2.10),(57,'Монитор',155.50),(59,'Мышка',30.00),(61,'HDD',25.50),(62,'Конфеты',5.60),(63,'Клавиатура',30.00),(64,'SSD',50.00);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_discounts`
--

DROP TABLE IF EXISTS `receipt_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_discounts` (
  `type` varchar(124) NOT NULL,
  `id` bigint NOT NULL,
  `dependent_discount_id` bigint DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpplo2q3jqfunvlr0nddje9cln` (`dependent_discount_id`),
  CONSTRAINT `FKpplo2q3jqfunvlr0nddje9cln` FOREIGN KEY (`dependent_discount_id`) REFERENCES `receipt_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_discounts`
--

LOCK TABLES `receipt_discounts` WRITE;
/*!40000 ALTER TABLE `receipt_discounts` DISABLE KEYS */;
INSERT INTO `receipt_discounts` VALUES ('SimplePercentageReceiptDiscount',2,NULL,'-35% на сумму чека'),('SimplePercentageReceiptDiscount',17,NULL,'-30% на сумму чека'),('SimplePercentageReceiptDiscount',35,NULL,'-20% на сумму чека'),('SimpleConstantReceiptDiscount',46,NULL,'-5$ на сумму чека'),('SimpleConstantReceiptDiscount',65,NULL,'-10$ на сумму чека'),('SimpleConstantReceiptDiscount',66,67,'-10$'),('SimplePercentageReceiptDiscount',67,NULL,'-35%'),('SimplePercentageReceiptDiscount',68,69,'-35%'),('SimpleConstantReceiptDiscount',69,NULL,'-10$'),('SimplePercentageReceiptDiscount',70,71,'-35%'),('SimpleConstantReceiptDiscount',71,72,'-10$'),('SimplePercentageReceiptDiscount',72,NULL,'-15%');
/*!40000 ALTER TABLE `receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_item_discounts`
--

DROP TABLE IF EXISTS `receipt_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_item_discounts` (
  `type` varchar(86) NOT NULL,
  `id` bigint NOT NULL,
  `dependent_discount_id` bigint DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgjn0brxm8mpgw8q04snabhx3v` (`dependent_discount_id`),
  CONSTRAINT `FKgjn0brxm8mpgw8q04snabhx3v` FOREIGN KEY (`dependent_discount_id`) REFERENCES `receipt_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_item_discounts`
--

LOCK TABLES `receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `receipt_item_discounts` VALUES ('ThresholdPercentageReceiptItemDiscount',13,NULL,'-10% если количество продукта больше чем 5'),('SimplePercentageReceiptItemDiscount',20,NULL,'-30%'),('ThresholdPercentageReceiptItemDiscount',22,23,'Скидка 1% если количество продуктов больше 6'),('SimpleConstantReceiptItemDiscount',23,NULL,'Скидка 10 на продукт'),('SimplePercentageReceiptItemDiscount',27,NULL,'-40%'),('SimpleConstantReceiptItemDiscount',49,NULL,'-5$'),('ThresholdPercentageReceiptItemDiscount',51,NULL,'Скидка 10% если количество продуктов больше 2'),('SimplePercentageReceiptItemDiscount',73,NULL,'-50%');
/*!40000 ALTER TABLE `receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_items`
--

DROP TABLE IF EXISTS `receipt_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_items` (
  `id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `quantity` bigint NOT NULL,
  `receipt_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK99g19542vt8s3r5eps10rusxx` (`product_id`,`receipt_id`),
  KEY `FKmyfhuc1y0sjqe2geey8jx9nc2` (`receipt_id`),
  CONSTRAINT `FKmyfhuc1y0sjqe2geey8jx9nc2` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`),
  CONSTRAINT `FKoflo2om70ovsg65ot9uqsk221` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_items`
--

LOCK TABLES `receipt_items` WRITE;
/*!40000 ALTER TABLE `receipt_items` DISABLE KEYS */;
INSERT INTO `receipt_items` VALUES (3,4,3,1),(5,6,1,1),(7,8,1,1),(9,10,9,1),(11,12,8,1),(14,15,2,1),(18,19,8,16),(21,12,8,16),(24,25,6,16),(26,15,7,16),(28,10,9,16),(29,30,5,16),(31,32,8,16),(33,6,10,16),(36,8,1,34),(37,32,11,34),(38,39,1,34),(40,41,6,34),(42,43,3,34),(44,12,15,34),(47,25,1,45),(48,19,15,45),(50,10,15,45),(52,30,3,45),(53,54,6,45),(55,15,1,45),(56,57,3,45),(58,59,11,45),(60,61,6,45);
/*!40000 ALTER TABLE `receipt_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipts`
--

DROP TABLE IF EXISTS `receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipts` (
  `id` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  `cashier` varchar(255) NOT NULL,
  `date` datetime(6) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKe6jbf81md2j9a1t1ombo151io` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipts`
--

LOCK TABLES `receipts` WRITE;
/*!40000 ALTER TABLE `receipts` DISABLE KEYS */;
INSERT INTO `receipts` VALUES (1,'ул. Пушкина, д. Калатушкина','Василий Пупкин','2021-03-20 21:30:43.303000','Компьютерный магазин','999 проблем','+375290000000'),(16,'ул. Элементова, д. 1','Екатерина Пупкина','2021-03-20 21:30:43.313000','Гипермаркет','342 элемент','+375290000001'),(34,'ул. Советская, д. 1001','Алексей Пупкин','2021-03-20 21:30:43.314000','Продуктовый магазин','Магазин №1','+375290000002'),(45,'ул. Свиридова, д. 1234','Татьяна Пупкина','2021-03-20 21:30:43.314000','Продуктовый магазин','Магазин №2','+375290000003');
/*!40000 ALTER TABLE `receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_constant_receipt_discounts`
--

DROP TABLE IF EXISTS `simple_constant_receipt_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_constant_receipt_discounts` (
  `constant` decimal(19,2) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK71yy9a5g72kvl5dnjp3i34jtm` FOREIGN KEY (`id`) REFERENCES `receipt_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_constant_receipt_discounts`
--

LOCK TABLES `simple_constant_receipt_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_receipt_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_receipt_discounts` VALUES (5.00,46),(10.00,65),(10.00,66),(10.00,69),(10.00,71);
/*!40000 ALTER TABLE `simple_constant_receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_constant_receipt_item_discounts`
--

DROP TABLE IF EXISTS `simple_constant_receipt_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_constant_receipt_item_discounts` (
  `constant` decimal(19,2) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKt1lvbhpjel4psw0km1xargqg1` FOREIGN KEY (`id`) REFERENCES `receipt_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_constant_receipt_item_discounts`
--

LOCK TABLES `simple_constant_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_receipt_item_discounts` VALUES (10.00,23),(5.00,49);
/*!40000 ALTER TABLE `simple_constant_receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_percentage_receipt_discounts`
--

DROP TABLE IF EXISTS `simple_percentage_receipt_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_percentage_receipt_discounts` (
  `percent` double NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKtoe3qwunljv5fkglpfd8mdapo` FOREIGN KEY (`id`) REFERENCES `receipt_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_percentage_receipt_discounts`
--

LOCK TABLES `simple_percentage_receipt_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_receipt_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_receipt_discounts` VALUES (35,2),(30,17),(20,35),(35,67),(35,68),(35,70),(15,72);
/*!40000 ALTER TABLE `simple_percentage_receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_percentage_receipt_item_discounts`
--

DROP TABLE IF EXISTS `simple_percentage_receipt_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `simple_percentage_receipt_item_discounts` (
  `percent` double NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKaypmms62s7s4ahj45adsp9l6l` FOREIGN KEY (`id`) REFERENCES `receipt_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_percentage_receipt_item_discounts`
--

LOCK TABLES `simple_percentage_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_receipt_item_discounts` VALUES (30,20),(40,27),(50,73);
/*!40000 ALTER TABLE `simple_percentage_receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `threshold_percentage_receipt_item_discounts`
--

DROP TABLE IF EXISTS `threshold_percentage_receipt_item_discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `threshold_percentage_receipt_item_discounts` (
  `percent` double NOT NULL,
  `threshold` bigint NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKarxmw5has101r0k4qx56a80av` FOREIGN KEY (`id`) REFERENCES `receipt_item_discounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `threshold_percentage_receipt_item_discounts`
--

LOCK TABLES `threshold_percentage_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `threshold_percentage_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `threshold_percentage_receipt_item_discounts` VALUES (10,5,13),(1,6,22),(10,2,51);
/*!40000 ALTER TABLE `threshold_percentage_receipt_item_discounts` ENABLE KEYS */;
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

-- Dump completed on 2021-03-20 21:38:44
