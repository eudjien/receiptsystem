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

-- Dump completed on 2021-03-20 21:37:20
