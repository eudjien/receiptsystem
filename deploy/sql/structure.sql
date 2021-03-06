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

-- Dump completed on 2021-03-03  7:20:53
