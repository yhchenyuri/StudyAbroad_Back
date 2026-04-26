CREATE DATABASE  IF NOT EXISTS `study_abroad_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `study_abroad_system`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: study_abroad_system
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '預約流水號',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '對外顯示用 (URL、API、查詢)',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '聯絡電話',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '電子郵件',
  `country_id` int DEFAULT NULL COMMENT '關聯到 countries.id',
  `requirement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '需求說明',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fk_appointments_country` (`country_id`),
  CONSTRAINT `fk_appointments_country` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,'5d7faa3f-3e19-11f1-bf1b-d8c497165b9d','王大明','0967123456','daming@gmail.com',1,'我有預算10萬以內、一個月的遊學。','2026-04-22 07:03:28');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '國家 ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '國家名稱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'台灣'),(2,'美國'),(3,'英國'),(4,'加拿大'),(5,'日本'),(6,'韓國'),(7,'新加坡'),(8,'越南'),(9,'泰國'),(10,'馬來西亞'),(11,'希臘');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '課程 ID',
  `course_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '課程名稱',
  `price` decimal(10,2) NOT NULL COMMENT '費用',
  `max_students` smallint unsigned DEFAULT NULL COMMENT '可報名人數',
  `current_enrollment` smallint unsigned DEFAULT '0' COMMENT '已報名人數',
  `start_date` date NOT NULL COMMENT '課程開始日期',
  `end_date` date NOT NULL COMMENT '課程結束日期',
  `duration_days` int GENERATED ALWAYS AS ((to_days(`end_date`) - to_days(`start_date`))) VIRTUAL COMMENT '自動計算的天數',
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '備註',
  `country_id` int DEFAULT NULL COMMENT '關聯到 countries.id',
  `is_active` tinyint(1) DEFAULT '1' COMMENT '1:啟用, 0:下架',
  PRIMARY KEY (`id`),
  KEY `fk_courses_country` (`country_id`),
  CONSTRAINT `fk_courses_country` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` (`id`, `course_name`, `price`, `max_students`, `current_enrollment`, `start_date`, `end_date`, `remarks`, `country_id`, `is_active`) VALUES (1,'哈佛大學 - 計算機科學學士學位課程',1850000.00,NULL,NULL,'2026-08-25','2027-05-20','秋季入學，需提交 SAT 核心成績',2,1),(2,'劍橋大學 - 數學學士學位課程',1450000.00,NULL,NULL,'2026-10-01','2027-06-25','必須通過 STEP 進階數學考試',3,1),(3,'史丹佛大學 - 工商管理碩士 (MBA)',2500000.00,NULL,NULL,'2026-09-01','2027-06-15','名額極度競爭，需兩輪面試',2,1),(4,'麻省理工學院 - 人工智慧博士研究',1950000.00,NULL,NULL,'2026-08-15','2027-08-14','全額獎學金計畫，附帶助教義務',2,1),(5,'加州大學柏克萊分校 - 經濟學碩士',1500000.00,NULL,NULL,'2026-08-20','2027-05-15','需提供高分 GRE 證明',2,1),(6,'哥倫比亞大學 - 新聞學碩士課程',1650000.00,NULL,NULL,'2026-09-05','2027-06-01','紐約校區，常春藤頂尖傳媒專案',2,1),(7,'賓州大學沃頓商學院 - 金融學士',1900000.00,NULL,NULL,'2026-08-28','2027-05-25','全美第一商學院，著重實務分析',2,1),(8,'耶魯大學 - 法律博士 (JD)',2100000.00,NULL,NULL,'2026-08-22','2027-05-18','要求 LSAT 頂標成績與卓越履歷',2,1),(9,'普林斯頓大學 - 天體物理學博士',1850000.00,NULL,NULL,'2026-09-01','2027-08-31','附高額研究補貼與住宿安排',2,1),(10,'紐約大學 - 電影製作碩士 (MFA)',1800000.00,NULL,NULL,'2026-09-10','2027-06-05','Tisch 藝術學院，需提交影音作品集',2,1),(11,'芝加哥大學 - 社會科學碩士',1400000.00,NULL,NULL,'2026-09-15','2027-06-10','高度重視量化研究能力與學術寫作',2,1),(12,'康乃爾大學 - 酒店管理學士',1600000.00,NULL,NULL,'2026-08-25','2027-05-20','包含業界知名飯店 800 小時實習要求',2,1),(13,'杜克大學 - 生物醫學工程碩士',1700000.00,NULL,NULL,'2026-08-20','2027-05-15','跨領域研究導向，結合醫學院資源',2,1),(14,'卡內基美隆大學 - 軟體工程碩士',1950000.00,NULL,NULL,'2026-08-15','2027-05-10','含矽谷知名科技公司暑期實習安排',2,1),(15,'西北大學 - 整合行銷傳播碩士',1550000.00,NULL,NULL,'2026-09-01','2027-06-20','Medill 學院招牌 IMC 專案',2,1),(16,'約翰霍普金斯大學 - 公共衛生碩士 (MPH)',1600000.00,NULL,NULL,'2026-08-10','2027-05-30','全美公衛排名第一，適合醫療從業人員',2,1),(17,'加州理工學院 - 物理學博士',1900000.00,NULL,NULL,'2026-09-01','2027-08-31','錄取率極低，專注基礎科學研究',2,1),(18,'南加州大學 - 數據科學碩士',1450000.00,NULL,NULL,'2026-08-25','2027-05-20','位於洛杉磯市中心，科技業就業網強大',2,1),(19,'喬治城大學 - 國際關係碩士',1500000.00,NULL,NULL,'2026-09-05','2027-05-25','位於 DC，鄰近各大國際組織與智庫',2,1),(20,'德州大學奧斯汀分校 - 石油工程碩士',1200000.00,NULL,NULL,'2026-08-20','2027-05-15','結合德州當地能源產業鏈優勢',2,1),(21,'密西根大學 - 機械工程學士',1350000.00,NULL,NULL,'2026-08-28','2027-05-22','安娜堡校區工程學院熱門科系',2,1),(22,'波士頓大學 - 臨床心理學碩士',1400000.00,NULL,NULL,'2026-09-01','2027-06-10','需具備心理學相關背景畢業方可申請',2,1),(23,'埃默里大學 - 護理學學士 (BSN)',1300000.00,NULL,NULL,'2026-08-25','2027-05-20','附屬醫院提供豐富臨床實習機會',2,1),(24,'萊斯大學 - 建築學碩士',1500000.00,NULL,NULL,'2026-08-20','2027-05-15','小班制菁英教學，師生比極佳',2,1),(25,'布朗大學 - 哲學博士',1950000.00,NULL,NULL,'2026-09-01','2027-08-31','常春藤盟校全額補助學術專案',2,1),(26,'華盛頓大學 - 藥學博士 (PharmD)',1750000.00,NULL,NULL,'2026-08-25','2027-05-20','與西雅圖頂級醫療中心深度合作',2,1),(27,'牛津大學 - 英國文學碩士 (MSt)',1300000.00,NULL,NULL,'2026-10-05','2027-06-20','體驗傳統學院制，重視獨立研究能力',3,1),(28,'倫敦帝國學院 - 機器學習碩士 (MSc)',1600000.00,NULL,NULL,'2026-09-28','2027-09-25','英國頂尖 STEM 專業，含企業專案',3,1),(29,'倫敦大學學院 (UCL) - 教育學博士',1200000.00,NULL,NULL,'2026-10-01','2027-09-30','IOE 學院，全球教育學科排名第一',3,1),(30,'倫敦政治經濟學院 (LSE) - 國際政治碩士',1500000.00,NULL,NULL,'2026-09-30','2027-06-30','地處倫敦政經中心，學術氛圍濃厚',3,1),(31,'愛丁堡大學 - 人工智慧碩士',1250000.00,NULL,NULL,'2026-09-15','2027-09-10','歐洲歷史最悠久的 AI 核心研究中心',3,1),(32,'曼徹斯特大學 - 土木工程學士',1100000.00,NULL,NULL,'2026-09-20','2027-06-15','英國傳統紅磚大學，工程領域底蘊深厚',3,1),(33,'倫敦國王學院 (KCL) - 法律學碩士 (LLM)',1350000.00,NULL,NULL,'2026-09-25','2027-06-20','法學院名列前茅，位於倫敦司法區旁',3,1),(34,'華威大學 - 商業分析碩士 (MSc)',1400000.00,NULL,NULL,'2026-10-01','2027-09-30','WBS 學院招牌專案，畢業生起薪極高',3,1),(35,'布里斯托大學 - 航空航太工程碩士',1200000.00,NULL,NULL,'2026-09-20','2027-09-15','與勞斯萊斯等航太巨頭有密切產學合作',3,1),(36,'格拉斯哥大學 - 醫學博士研究',1200000.00,NULL,NULL,'2026-10-01','2027-09-30','由英國國民保健署(NHS)研究基金贊助',3,1),(37,'伯明罕大學 - 心理學學士',1050000.00,NULL,NULL,'2026-09-22','2027-06-18','受英國心理學會 (BPS) 完全認證課程',3,1),(38,'南安普敦大學 - 海洋科學碩士',1150000.00,NULL,NULL,'2026-09-25','2027-09-20','英國國家海洋中心所在地，實地探勘多',3,1),(39,'聖安德魯斯大學 - 歷史學學士',1200000.00,NULL,NULL,'2026-10-01','2027-06-25','蘇格蘭最古老大學，學風嚴謹古典',3,1),(40,'杜倫大學 - 神學與宗教碩士',1100000.00,NULL,NULL,'2026-10-05','2027-09-30','世界百大名校，學院制生活體驗佳',3,1),(41,'雪菲爾大學 - 景觀建築碩士',1180000.00,NULL,NULL,'2026-09-20','2027-09-15','英國景觀設計專業首選，作品集要求高',3,1),(42,'里茲大學 - 數位傳播碩士',1080000.00,NULL,NULL,'2026-09-28','2027-09-20','傳播與媒體學院年度最熱門申請科系',3,1),(43,'諾丁漢大學 - 藥劑學學士',1250000.00,NULL,NULL,'2026-09-22','2027-06-15','含完整臨床實習培訓的專業學位',3,1),(44,'倫敦藝術大學 (UAL) - 時尚設計碩士',1550000.00,NULL,NULL,'2026-10-01','2027-09-30','倫敦時尚學院專案，提供時裝週參展機會',3,1),(45,'利物浦大學 - 足球管理碩士 (MBA)',1300000.00,NULL,NULL,'2026-10-10','2027-10-05','英國獨有的特色體育管理高階課程',3,1),(46,'卡迪夫大學 - 城市規劃碩士',1050000.00,NULL,NULL,'2026-09-25','2027-09-20','位於威爾斯首府，都市更新研究資源豐富',3,1),(47,'貝爾法斯特女王大學 - 網路安全碩士',1100000.00,NULL,NULL,'2026-10-01','2027-09-30','獲英國政府國家網路安全中心 (NCSC) 認證',3,1),(48,'巴斯大學 - 翻譯與口譯碩士 (MA)',1200000.00,NULL,NULL,'2026-09-15','2027-06-30','聯合國級別口譯高強度訓練，錄取門檻極高',3,1),(49,'艾希特大學 - 環境科學博士',1120000.00,NULL,NULL,'2026-10-01','2027-09-30','專注全球氣候變遷與永續發展深度研究，原始學費不含獎學金抵免',3,1),(50,'約克大學 - 考古學學士',1000000.00,NULL,NULL,'2026-09-28','2027-06-20','結合約克歷史古城優勢，田野調查實作多',3,1);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '員工編號',
  `employee_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '員工工號 (唯一)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作信箱 (唯一)',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登入密碼 (雜湊值)',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所屬部門',
  `role` enum('admin','consultant','staff') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'staff' COMMENT '角色權限',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '聯絡電話',
  `is_active` tinyint(1) DEFAULT '1' COMMENT '1: 在職, 0: 離職',
  `last_login_at` timestamp NULL DEFAULT NULL COMMENT '最後登入時間',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `employee_code` (`employee_code`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'1','大吉','chihming.wang@example.com','hashed_password_123','Sales','admin','0912-345-678',1,'2026-04-20 01:15:23','2025-12-01 02:00:00'),(2,'2','大利','yating.lin@example.com','hashed_password_456','Sales','staff','0923-567-890',1,'2026-04-19 09:42:10','2026-01-15 01:30:00');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '訂單流水號',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '訂單外部識別碼',
  `student_id` int NOT NULL COMMENT '學生 ID',
  `course_id` int NOT NULL COMMENT '課程 ID',
  `employee_id` int DEFAULT NULL COMMENT '經辦員工 ID',
  `original_price` decimal(10,2) NOT NULL COMMENT '原始金額',
  `discount_rate` decimal(5,2) DEFAULT '0.00' COMMENT '折扣 % 數',
  `final_price` decimal(10,2) NOT NULL COMMENT '實收金額',
  `order_status` enum('待付款','已完款','退費','已取消') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '待付款' COMMENT '訂單狀態',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下單時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fk_orders_student` (`student_id`),
  KEY `fk_orders_course` (`course_id`),
  KEY `fk_orders_employee` (`employee_id`),
  CONSTRAINT `fk_orders_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `fk_orders_employee` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `fk_orders_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,'1c0a2330-3e1a-11f1-bf1b-d8c497165b9d',1,1,1,150000.00,10.00,135000.00,'已完款','2026-04-22 07:08:48'),(6,'1c0aa9f0-3e1a-11f1-bf1b-d8c497165b9d',2,2,1,85000.00,0.00,85000.00,'待付款','2026-04-22 07:08:48');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '學生編號',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '外部辨識碼',
  `name_zh` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名 (中文)',
  `name_en` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名 (英文)',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Email (唯一索引)',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密碼 (加密)',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `gender` enum('男','女','其他') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性別',
  `country_id` int DEFAULT NULL COMMENT '國籍',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手機號碼',
  `passport_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '護照號碼',
  `ice_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '緊急聯絡人',
  `ice_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '緊急聯絡人電話',
  `appointment_id` int DEFAULT NULL COMMENT '由哪筆預約轉入',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '註冊時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_students_country` (`country_id`),
  KEY `fk_students_appointment` (`appointment_id`),
  CONSTRAINT `fk_students_appointment` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_students_country` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'d5fcc4de-3d4f-11f1-bd0a-a2cb13febfc3','王子豪','Wang, Tzu-Hao','tzuhao.wang01@testmail.com','$2a$10$e1IPigrbm/6XmQ.MUAmZ3eJ4XprBWUCu/luModw1VNAyWDrV8Om0e','1998-07-15','男',1,'0912-345-678','E12345678','王美玲','0923-456-789',NULL,'2026-04-21 07:00:52'),(2,'d5fcd1c2-3d4f-11f1-bd0a-a2cb13febfc3','林雅婷','Lin, Ya-Ting','yating.lin02@testmail.com','$2a$10$VTBLYiulcYdSvlkEhXhGXeVQHW3fvUj3ylDdEa3186a0cqryFD1tG','2000-11-03','女',1,'0935-678-912','F87654321','林志明','0911-223-344',NULL,'2026-04-21 07:00:52');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'study_abroad_system'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-22 15:25:12
