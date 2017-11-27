-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: db_blaboard
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feed` (
  `topic_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  KEY `FK_topic` (`topic_id`),
  KEY `FK_user` (`user_id`),
  CONSTRAINT `FK_topic` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`topic_id`),
  CONSTRAINT `FK_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum_messages`
--

DROP TABLE IF EXISTS `forum_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum_messages` (
  `message_id` int(10) NOT NULL AUTO_INCREMENT,
  `message_author_id` int(10) NOT NULL,
  `message_text` varchar(2500) NOT NULL,
  `message_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message_rating` int(10) NOT NULL DEFAULT '0',
  `message_edit_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message_topic_id` int(10) NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `FK_message_author` (`message_author_id`),
  KEY `FK_message_topic` (`message_topic_id`),
  CONSTRAINT `FK_message_author` FOREIGN KEY (`message_author_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_message_topic` FOREIGN KEY (`message_topic_id`) REFERENCES `topics` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_messages`
--

LOCK TABLES `forum_messages` WRITE;
/*!40000 ALTER TABLE `forum_messages` DISABLE KEYS */;
INSERT INTO `forum_messages` VALUES (18,41,'<p>+</p>','2017-11-23 15:51:27',0,'2017-11-23 15:51:27',26),(19,41,'','2017-11-23 15:53:31',0,'2017-11-23 15:53:31',27),(20,41,'<p>ого</p>','2017-11-23 15:55:29',0,'2017-11-23 15:55:29',29),(21,41,'<p>456</p>','2017-11-23 16:53:58',0,'2017-11-23 16:53:58',28),(22,41,'<p>edfddf</p>','2017-11-23 23:36:39',0,'2017-11-23 23:36:39',28),(23,41,'','2017-11-24 16:51:59',0,'2017-11-24 16:51:59',31),(24,41,'<p>wewewewewewe</p>','2017-11-24 16:52:55',0,'2017-11-24 16:52:55',31),(25,41,'<p>24352t2443frwdsdv</p>','2017-11-24 19:39:30',0,'2017-11-24 19:39:30',26);
/*!40000 ALTER TABLE `forum_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forums`
--

DROP TABLE IF EXISTS `forums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forums` (
  `forum_id` int(10) NOT NULL AUTO_INCREMENT,
  `forum_name` varchar(30) NOT NULL,
  `forum_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forums`
--

LOCK TABLES `forums` WRITE;
/*!40000 ALTER TABLE `forums` DISABLE KEYS */;
INSERT INTO `forums` VALUES (1,'HTML форум',NULL),(2,'JAVA форум',NULL);
/*!40000 ALTER TABLE `forums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sections` (
  `section_id` int(10) NOT NULL AUTO_INCREMENT,
  `section_name` varchar(30) NOT NULL,
  `section_forum_id` int(10) NOT NULL,
  `section_desc` varchar(50) DEFAULT NULL,
  `section_parent_id` int(10) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`section_id`),
  KEY `FK_forum` (`section_forum_id`),
  CONSTRAINT `FK_forum` FOREIGN KEY (`section_forum_id`) REFERENCES `forums` (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES (1,'Начинающим',1,'Вопросы тех, кто только изучает веб-технологии.',-1),(3,'Java EE',2,'Вопросы, связанные с Java EE',-1),(4,'Java SE',2,'Вопросы, связанные с Java SE',-1),(5,'Подраздел',1,'ыовлыовлол',6),(6,'Раздел',1,'dsdsdsd',-1);
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `tag_name` varchar(50) NOT NULL,
  `tag_topic_id` int(11) NOT NULL,
  PRIMARY KEY (`tag_name`,`tag_topic_id`),
  KEY `FK_tag_topic` (`tag_topic_id`),
  CONSTRAINT `FK_tag_topic` FOREIGN KEY (`tag_topic_id`) REFERENCES `topics` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES ('кукукуку',30);
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topics` (
  `topic_id` int(10) NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(50) NOT NULL,
  `topic_section_id` int(10) NOT NULL,
  `topic_author_id` int(10) NOT NULL,
  `topic_status` int(1) NOT NULL DEFAULT '1',
  `topic_text` varchar(2000) NOT NULL,
  `topic_views` int(10) NOT NULL DEFAULT '0',
  `topic_create_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `topic_edit_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`topic_id`),
  KEY `FK_section` (`topic_section_id`),
  KEY `FK_author` (`topic_author_id`),
  CONSTRAINT `FK_author` FOREIGN KEY (`topic_author_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_section` FOREIGN KEY (`topic_section_id`) REFERENCES `sections` (`section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (26,'CSS',1,41,1,'<p>Для чего нужен CSS? В чем его особенности и как его использовать?</p>',0,'2017-11-23 15:51:09','2017-11-23 15:51:09'),(27,'Android',1,41,1,'<p>Архитектура ОС</p>',0,'2017-11-23 15:53:12','2017-11-23 15:53:12'),(28,'цуцуцу',1,41,1,'<p>цуцуцуцуцуцуцик</p>',0,'2017-11-23 15:54:38','2017-11-23 15:54:38'),(29,'цуцуууу',3,41,1,'<p>цуууууууууууууууууууууууууууууууууууууууцик</p>',0,'2017-11-23 15:55:12','2017-11-23 15:55:12'),(30,'Title',4,41,1,'<p>что-то!)</p>',0,'2017-11-23 23:29:24','2017-11-23 23:29:24'),(31,'Introduction to Web Services',3,41,1,'<p style=\"font-size: 13px; margin: 1em 0px; color: #222222; font-family: Arial, Helvetica, sans-serif;\">This part of the tutorial discusses Java EE 7 web services technologies. These technologies include Java API for XML Web Services (JAX-WS) and Java API for RESTful Web Services (JAX-RS).</p>\r\n<p style=\"font-size: 13px; margin: 1em 0px; color: #222222; font-family: Arial, Helvetica, sans-serif;\">The following topics are addressed here:</p>\r\n<ul style=\"margin: 1em 0px; padding: 0px 0px 0px 40px; color: #222222; font-family: Arial, Helvetica, sans-serif; font-size: 13px;\">\r\n<li>\r\n<p style=\"margin: 1em 0px;\"><a style=\"text-decoration-line: none; color: #3a87cf;\" href=\"https://docs.oracle.com/javaee/7/tutorial/webservices-intro001.htm#GIJVH\">What Are Web Services?</a></p>\r\n</li>\r\n<li>\r\n<p style=\"margin: 1em 0px;\"><a style=\"text-decoration-line: none; color: #3a87cf;\" href=\"https://docs.oracle.com/javaee/7/tutorial/webservices-intro002.htm#GIQSX\">Types of Web Services</a></p>\r\n</li>\r\n<li>\r\n<p style=\"margin: 1em 0px;\"><a style=\"text-decoration-line: none; color: #3a87cf;\" href=\"https://docs.oracle.com/javaee/7/tutorial/webservices-intro003.htm#GJBJI\">Deciding Which Type of Web Service to Use</a></p>\r\n</li>\r\n</ul>',0,'2017-11-24 16:51:51','2017-11-24 16:51:51'),(32,'qwerty',1,43,1,'<p>qwerty</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>PS. Sulaymon</p>',0,'2017-11-24 19:46:16','2017-11-24 19:46:16');
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_role` varchar(50) NOT NULL,
  `uuid` char(50) DEFAULT NULL,
  `delete_date` date DEFAULT NULL,
  `user_active` bit(1) DEFAULT b'0',
  `user_confirmed` bit(1) DEFAULT b'0',
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_email` varchar(30) NOT NULL,
  `user_fname` varchar(20) DEFAULT NULL,
  `user_lname` varchar(20) DEFAULT NULL,
  `user_info` varchar(200) DEFAULT NULL,
  `user_rating` int(11) NOT NULL DEFAULT '0',
  `user_register_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (41,'ADMIN','6f409f97-b322-478a-a159-f0db05dcd963','2017-11-28','\0','\0','ivan17','e10adc3949ba59abbe56e057f20f883e','zhestkov.ivan17@gmail.com','Ivan','Zhestkov',NULL,0,'2017-11-23 15:49:53'),(42,'MEMBER','83eccc9e-263a-47ed-92f6-57e031286aed','2017-11-29','\0','\0','User1','897c8fde25c5cc5270cda61425eed3c8','zjefznej@mail.ru','USER1','user','<p>f</p>',0,'2017-11-24 14:22:07'),(43,'MEMBER','e44a3508-dc72-4377-9091-2572db954de8','2017-11-29','\0','\0','sulaymon','6eb77d55f6c6ebb5eddf310eab6aa724','demir@ht','sulaymon','hursanov',NULL,0,'2017-11-24 19:45:33');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-26 21:52:46
