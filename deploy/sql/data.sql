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
-- Dumping data for table `_receipt__receipt_discount`
--

LOCK TABLES `_receipt__receipt_discount` WRITE;
/*!40000 ALTER TABLE `_receipt__receipt_discount` DISABLE KEYS */;
INSERT INTO `_receipt__receipt_discount` VALUES (1,2),(16,17),(34,35),(45,46);
/*!40000 ALTER TABLE `_receipt__receipt_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `_receipt_item__receipt_item_discount`
--

LOCK TABLES `_receipt_item__receipt_item_discount` WRITE;
/*!40000 ALTER TABLE `_receipt_item__receipt_item_discount` DISABLE KEYS */;
INSERT INTO `_receipt_item__receipt_item_discount` VALUES (5,7),(21,22),(24,25),(31,33),(58,59),(60,61);
/*!40000 ALTER TABLE `_receipt_item__receipt_item_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `emails`
--

LOCK TABLES `emails` WRITE;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
INSERT INTO `emails` VALUES (75,'chcksstm1@gmail.com'),(74,'lakadmakatag@gmail.com');
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event_emails`
--

LOCK TABLES `event_emails` WRITE;
/*!40000 ALTER TABLE `event_emails` DISABLE KEYS */;
INSERT INTO `event_emails` VALUES (76,74,'PRINT_END'),(77,75,'PRINT_END');
/*!40000 ALTER TABLE `event_emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (78);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (4,'Картошка',7.50),(6,'Помидоры',3.91),(9,'Макароны',8.50),(11,'Сало',10.00),(13,'Кукуруза',20.00),(15,'Пельмени',12.48),(19,'Яблоки',8.70),(27,'Рис',20.00),(30,'Апельсины',30.50),(32,'Чай',3.51),(37,'Рыба',50.20),(39,'Кофе',3.50),(43,'Колбаса',20.00),(48,'Монитор',155.50),(50,'HDD',25.50),(55,'Мышка',30.00),(57,'Печенье',2.10),(62,'Конфеты',5.60),(63,'Клавиатура',30.00),(64,'SSD',50.00);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `receipt_discounts`
--

LOCK TABLES `receipt_discounts` WRITE;
/*!40000 ALTER TABLE `receipt_discounts` DISABLE KEYS */;
INSERT INTO `receipt_discounts` VALUES ('SimplePercentageReceiptDiscount',2,NULL,'-35% на сумму чека'),('SimplePercentageReceiptDiscount',17,NULL,'-30% на сумму чека'),('SimplePercentageReceiptDiscount',35,NULL,'-20% на сумму чека'),('SimpleConstantReceiptDiscount',46,NULL,'-5$ на сумму чека'),('SimpleConstantReceiptDiscount',65,NULL,'-10$ на сумму чека'),('SimpleConstantReceiptDiscount',66,67,'-10$'),('SimplePercentageReceiptDiscount',67,NULL,'-35%'),('SimplePercentageReceiptDiscount',68,69,'-35%'),('SimpleConstantReceiptDiscount',69,NULL,'-10$'),('SimplePercentageReceiptDiscount',70,71,'-35%'),('SimpleConstantReceiptDiscount',71,72,'-10$'),('SimplePercentageReceiptDiscount',72,NULL,'-15%');
/*!40000 ALTER TABLE `receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `receipt_item_discounts`
--

LOCK TABLES `receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `receipt_item_discounts` VALUES ('ThresholdPercentageReceiptItemDiscount',7,NULL,'-10% если количество продукта больше чем 5'),('ThresholdPercentageReceiptItemDiscount',22,23,'Скидка 1% если количество продуктов больше 6'),('SimpleConstantReceiptItemDiscount',23,NULL,'Скидка 10 на продукт'),('SimplePercentageReceiptItemDiscount',25,NULL,'-40%'),('SimplePercentageReceiptItemDiscount',33,NULL,'-30%'),('SimpleConstantReceiptItemDiscount',59,NULL,'-5$'),('ThresholdPercentageReceiptItemDiscount',61,NULL,'Скидка 10% если количество продуктов больше 2'),('SimplePercentageReceiptItemDiscount',73,NULL,'-50%');
/*!40000 ALTER TABLE `receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `receipt_items`
--

LOCK TABLES `receipt_items` WRITE;
/*!40000 ALTER TABLE `receipt_items` DISABLE KEYS */;
INSERT INTO `receipt_items` VALUES (3,4,1,1),(5,6,8,1),(8,9,2,1),(10,11,9,1),(12,13,1,1),(14,15,3,1),(18,19,8,16),(20,4,10,16),(21,6,8,16),(24,9,7,16),(26,27,6,16),(28,11,9,16),(29,30,5,16),(31,32,8,16),(36,37,3,34),(38,39,6,34),(40,19,11,34),(41,6,15,34),(42,43,1,34),(44,13,1,34),(47,48,3,45),(49,50,6,45),(51,30,3,45),(52,27,1,45),(53,9,1,45),(54,55,11,45),(56,57,6,45),(58,32,15,45),(60,11,15,45);
/*!40000 ALTER TABLE `receipt_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `receipts`
--

LOCK TABLES `receipts` WRITE;
/*!40000 ALTER TABLE `receipts` DISABLE KEYS */;
INSERT INTO `receipts` VALUES (1,'ул. Пушкина, д. Калатушкина','Василий Пупкин','2021-03-12 16:48:35.614000','Компьютерный магазин','999 проблем','+375290000000'),(16,'ул. Элементова, д. 1','Екатерина Пупкина','2021-03-12 16:48:35.636000','Гипермаркет','342 элемент','+375290000001'),(34,'ул. Советская, д. 1001','Алексей Пупкин','2021-03-12 16:48:35.636000','Продуктовый магазин','Магазин №1','+375290000002'),(45,'ул. Свиридова, д. 1234','Татьяна Пупкина','2021-03-12 16:48:35.636000','Продуктовый магазин','Магазин №2','+375290000003');
/*!40000 ALTER TABLE `receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `simple_constant_receipt_discounts`
--

LOCK TABLES `simple_constant_receipt_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_receipt_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_receipt_discounts` VALUES (5.00,46),(10.00,65),(10.00,66),(10.00,69),(10.00,71);
/*!40000 ALTER TABLE `simple_constant_receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `simple_constant_receipt_item_discounts`
--

LOCK TABLES `simple_constant_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_constant_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_constant_receipt_item_discounts` VALUES (10.00,23),(5.00,59);
/*!40000 ALTER TABLE `simple_constant_receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `simple_percentage_receipt_discounts`
--

LOCK TABLES `simple_percentage_receipt_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_receipt_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_receipt_discounts` VALUES (35,2),(30,17),(20,35),(35,67),(35,68),(35,70),(15,72);
/*!40000 ALTER TABLE `simple_percentage_receipt_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `simple_percentage_receipt_item_discounts`
--

LOCK TABLES `simple_percentage_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `simple_percentage_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `simple_percentage_receipt_item_discounts` VALUES (40,25),(30,33),(50,73);
/*!40000 ALTER TABLE `simple_percentage_receipt_item_discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold_percentage_receipt_item_discounts`
--

LOCK TABLES `threshold_percentage_receipt_item_discounts` WRITE;
/*!40000 ALTER TABLE `threshold_percentage_receipt_item_discounts` DISABLE KEYS */;
INSERT INTO `threshold_percentage_receipt_item_discounts` VALUES (10,5,7),(1,6,22),(10,2,61);
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

-- Dump completed on 2021-03-12 18:39:32
