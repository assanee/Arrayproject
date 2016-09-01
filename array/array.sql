-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 01, 2016 at 08:38 PM
-- Server version: 5.5.50-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `array`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE IF NOT EXISTS `branch` (
  `id_branch` int(30) NOT NULL AUTO_INCREMENT,
  `id_shop` int(30) NOT NULL,
  `name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mobile_number` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `latitude` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `longitude` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_branch`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`id_branch`, `id_shop`, `name`, `mobile_number`, `phone_number`, `latitude`, `longitude`, `created_at`) VALUES
(1, 1, 'mimi shop2', '01478523696', '01478523696', '16.753315', '100.196148', '2016-08-15 09:54:55'),
(2, 2, 'shop12', '01478523696', '01478523696', '16.753708', '100.194965', '2016-08-15 13:54:55'),
(3, 3, 'shop22', '01478523696', '01478523696', '16.749116', '100.198454', '2016-08-15 13:54:55'),
(4, 4, 'shop32', '01478523696', '01478523696', '16.739887', '100.189851', '2016-08-15 13:54:55'),
(5, 5, 'shop42', '01478523696', '01478523696', '16.745553', '100.201959', '2016-08-15 13:54:55'),
(6, 6, 'shop52', '01478523696', '01478523696', '16.746683', '100.203590', '2016-08-15 13:54:55'),
(7, 7, 'shop62', '01478523696', '01478523696', '16.749755', '100.201627', '2016-08-15 13:54:55'),
(8, 8, 'shop72', '01478523696', '01478523696', '16.751758', '100.198902', '2016-08-15 13:54:55'),
(9, 9, 'shop82', '01478523696', '01478523696', '16.753145', '100.201155', '2016-08-15 13:54:55'),
(10, 10, 'shop92', '01478523696', '01478523696', '16.754049', '100.199342', '2016-08-15 13:54:55'),
(11, 16, 'fgdfgdfg', 'dfgdfgdf', 'gdfgdfg', 'dfgdfgdf', 'dfgdfgdfg', '2016-08-27 13:04:34'),
(12, 16, 'fgdfgdfg', 'dfgdfgdf', 'gdfgdfg', 'dfgdfgdf', 'dfgdfgdfg', '2016-08-27 13:23:22'),
(13, 1, 'mimi222', '22222', '22222', '22222', '22222', '2016-08-27 14:47:47');

-- --------------------------------------------------------

--
-- Table structure for table `favorite`
--

CREATE TABLE IF NOT EXISTS `favorite` (
  `id_favorite` int(30) NOT NULL AUTO_INCREMENT,
  `id_user` int(30) NOT NULL,
  `id_branch` int(30) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_favorite`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `favorite`
--

INSERT INTO `favorite` (`id_favorite`, `id_user`, `id_branch`, `created_at`) VALUES
(1, 3, 1, '2016-08-15 09:55:38'),
(2, 3, 2, '2016-08-15 13:55:38'),
(3, 3, 3, '2016-08-15 13:55:38'),
(4, 3, 4, '2016-08-15 13:55:38'),
(5, 3, 5, '2016-08-15 13:55:38'),
(6, 3, 6, '2016-08-15 13:55:38'),
(7, 3, 7, '2016-08-15 13:55:38'),
(11, 13, 4, '2016-08-25 06:20:28'),
(13, 3, 8, '2016-09-01 22:08:53'),
(14, 3, 10, '2016-09-01 22:18:31');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id_queue` int(30) NOT NULL AUTO_INCREMENT,
  `id_branch` int(30) NOT NULL,
  `id_user` int(30) NOT NULL,
  `id_promotion` int(30) NOT NULL,
  `detail` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_queue`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id_queue`, `id_branch`, `id_user`, `id_promotion`, `detail`, `created_at`) VALUES
(1, 5, 3, 5, 'mimi', '2016-08-20 06:11:06');

-- --------------------------------------------------------

--
-- Table structure for table `promotion`
--

CREATE TABLE IF NOT EXISTS `promotion` (
  `id_promotion` int(30) NOT NULL AUTO_INCREMENT,
  `id_branch` int(30) NOT NULL,
  `name_promotion` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `logo_mini` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `logo_full` text CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `detail` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_promotion` date NOT NULL,
  `end_promotion` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_promotion`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `promotion`
--

INSERT INTO `promotion` (`id_promotion`, `id_branch`, `name_promotion`, `logo_mini`, `logo_full`, `detail`, `start_promotion`, `end_promotion`, `created_at`) VALUES
(1, 1, 'Test protion 1', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-02', '2016-09-22', '2016-08-19 08:03:08'),
(2, 2, 'Test protion 2', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-09-01', '2016-09-17', '2016-08-19 08:03:25'),
(3, 3, 'Test protion 12', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-19', '2016-08-20', '2016-08-19 08:03:27'),
(4, 4, 'Test protion 12zxczc', 'timthumb.png', 'tysdjhyetgnsdgzmd.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-15', '2016-08-25', '2016-08-19 08:03:31'),
(5, 5, 'Test protion 1cascasc', 'timthumb.png', 'drfdgbsr5gsgr.png', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-01', '2016-08-24', '2016-08-19 08:03:33'),
(6, 6, 'Test protion 1ascascasacazasdsfssdsdsss', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-02', '2016-08-06', '2016-08-19 08:03:35'),
(7, 7, 'Test protion 1ascascasc', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-24', '2016-08-31', '2016-08-19 08:03:48'),
(8, 8, 'Test protion 1ascascascuitgszfhjdg', 'timthumb.png', '70d422d5972596f603a94c0faf24a43d.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-19', '2016-08-20', '2016-08-19 08:03:56'),
(9, 9, 'Test protion 1ascascadfas', 'timthumb.png', '9f372c463148c7133aaba2d01983d6f1.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-22', '2016-08-31', '2016-08-19 08:03:56'),
(10, 1, 'Test protion 1ascascdfgf', 'timthumb.png', 'tysdjhyetgnsdgzmd.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2016-08-19', '2016-08-31', '2016-08-19 08:03:56'),
(11, 1, 'Test protion 1ascascvgdfg', 'timthumb.png', 'tysdjhyetgnsdgzmd.jpg', 'สิทธิพิเศษจาก Thanachart ClubDD แอพที่ให้ สิทธิพิเศษดีๆ ฟรีๆ ไม่เสียเงิน สัมผัสรสชาติ เข้มข้น เพื่อคอกาแฟ เมื่อซื้อกาแฟ Kuppadelli เมนูใดก็ได้ 1 แก้ว รับฟรี! อีก 1 แก้ว เติมความสดชื่นในวันทำงานด้วยกาแฟรสละมุน ดื่มด่ำกับกลิ่นหอมกรุ่นและรสชาติก็นุ่มละมุนลิ้นจาก Kuppadelli  ร้านดังที่คอกาแฟคุ้นเคยกันเป็นอย่างดี พร้อมโปรโมชั่นสุดคุ้มเฉพาะสมาชิก Thanachart ClubDD เท่านั้น!!! รับสิทธิ์ซื้อกาแฟ 1 แถม 1 ฟรี!! ', '2017-03-24', '2017-09-15', '2016-08-19 08:03:56'),
(12, 3, '3', '3', '3', '3', '2016-08-02', '2016-08-03', '2016-08-27 19:04:09'),
(13, 3, 'pppppppp', 'Array', 'Array', 'a53e787b9b6d562e701e9a2a3446bfa7', '2016-12-12', '2016-12-12', '2016-08-27 19:13:58'),
(14, 3, 'pppppppp', '67becb589810b4a159434146d307cf99.png', '7b02eca169eaffaace0793f9cf3fdd58.png', 'a53e787b9b6d562e701e9a2a3446bfa7', '2016-12-12', '2016-12-12', '2016-08-27 19:16:52'),
(15, 3, 'pppppppp', '21ab79346ff7c593624c9d66cda2c7cc.png', '2285816fe754224c2b7d215c9080f0e4.png', 'a53e787b9b6d562e701e9a2a3446bfa7', '2016-12-12', '2016-12-12', '2016-08-27 19:17:28'),
(16, 3, 'pppppppp', 'dfd3f8249978f2e6ebc1f591b35b01e6.png', '802da2aad56938a11f1553e8974af57c.png', 'a53e787b9b6d562e701e9a2a3446bfa7', '2016-12-12', '2016-12-12', '2016-08-27 19:17:39'),
(18, 12, 'sxsxasx', 'cde348f3a2e89de04728ee5f30a21b93.png', '643ec46a7d9cbb8c63fbe0288be0c3e2.png', 'sadadasdasasasasasasasasasas', '2016-08-13', '2016-08-27', '2016-08-27 22:55:58'),
(19, 11, 'sdsadasd', '30f6f61aef4a4b43b853951939611854.png', '847553076ba5ff3f54b1c80312fbb5e6.jpg', 'asdasdasd', '2016-08-02', '2016-08-17', '2016-08-27 23:06:11'),
(20, 11, 'fsdffffffffff', 'c83a55769b17e136625754af0ec40874.png', 'f7f4b3d1886120228a47618eb270ed11.jpg', 'rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr', '2016-08-08', '2016-08-31', '2016-08-27 23:09:29'),
(21, 11, 'pppppppppppppppppp', 'a2ba820f6f7827514a4b54670af2f927.png', 'e0289c86c0953f3b3545725c0cb2ee59.png', 'ppppppppppppppppppppppp', '2016-08-25', '2016-09-16', '2016-08-27 23:19:39');

-- --------------------------------------------------------

--
-- Table structure for table `queue`
--

CREATE TABLE IF NOT EXISTS `queue` (
  `id_queue` int(30) NOT NULL AUTO_INCREMENT,
  `id_branch` int(30) NOT NULL,
  `id_user` int(30) NOT NULL,
  `table_type` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `number` int(11) NOT NULL,
  `code` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_queue`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Dumping data for table `queue`
--

INSERT INTO `queue` (`id_queue`, `id_branch`, `id_user`, `table_type`, `number`, `code`, `status`, `created_at`) VALUES
(32, 2, 3, '6+', 1, 'A001', 'book', '2016-08-21 05:41:12'),
(33, 6, 3, '6+', 1, 'A000', 'book', '2016-08-21 08:23:05');

-- --------------------------------------------------------

--
-- Table structure for table `queue_status`
--

CREATE TABLE IF NOT EXISTS `queue_status` (
  `id_queue_status` int(30) NOT NULL AUTO_INCREMENT,
  `id_branch` int(30) NOT NULL,
  `id_queue` int(30) NOT NULL,
  `detail` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_queue_status`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `queue_status`
--

INSERT INTO `queue_status` (`id_queue_status`, `id_branch`, `id_queue`, `detail`, `created_at`) VALUES
(1, 1, 1, 'book', '2016-08-15 09:55:22'),
(2, 4, 3, 'book', '2016-08-19 18:16:35'),
(3, 5, 3, 'book', '2016-08-19 18:27:17'),
(4, 6, 3, 'book', '2016-08-19 18:27:29'),
(5, 7, 3, 'book', '2016-08-19 18:30:33'),
(6, 8, 3, 'book', '2016-08-19 18:30:36'),
(7, 9, 3, 'book', '2016-08-19 18:34:57'),
(8, 3, 3, 'book', '2016-08-19 18:36:09'),
(9, 3, 32, 'book', '2016-08-21 04:45:22'),
(10, 3, 32, 'book', '2016-08-21 04:48:47'),
(11, 2, 32, 'book', '2016-08-21 05:41:12'),
(12, 6, 33, 'book', '2016-08-21 08:23:05');

-- --------------------------------------------------------

--
-- Table structure for table `shop`
--

CREATE TABLE IF NOT EXISTS `shop` (
  `id_shop` int(30) NOT NULL AUTO_INCREMENT,
  `id_user` int(30) NOT NULL,
  `name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `logo` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mobile_number` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_shop`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `shop`
--

INSERT INTO `shop` (`id_shop`, `id_user`, `name`, `logo`, `mobile_number`, `phone_number`, `created_at`) VALUES
(1, 14, 'mimi shop', 'mimishop.png', '01478523696', '01478523696', '2016-08-15 09:47:42'),
(2, 140, 'shop1', 'shop1.png', '0821740041', '055021236', '2016-08-15 10:09:08'),
(3, 15, 'shop2', 'shop2.png', '0821740041', '055021236', '2016-08-15 10:09:34'),
(4, 16, 'shop3', 'shop3.png', '0821740041', '055021236', '2016-08-15 10:09:45'),
(5, 17, 'shop4', 'shop4.png', '0821740041', '055021236', '2016-08-15 10:09:57'),
(6, 18, 'shop5', 'shop5.png', '0821740041', '055021236', '2016-08-15 10:10:12'),
(7, 19, 'shop6', 'shop6.png', '0821740041', '055021236', '2016-08-15 10:10:28'),
(8, 20, 'shop7', 'shop7.png', '0821740041', '055021236', '2016-08-15 10:10:45'),
(9, 21, 'shop8', 'shop8.png', '0821740041', '055021236', '2016-08-15 10:10:57'),
(10, 22, 'shop9', 'shop9.png', '0821740041', '055021236', '2016-08-15 10:11:12'),
(16, 13, 'My shop', '3e3084b83cd88bbc1cdf10b7b83a9f35.png', '0821740041', '021236525', '2016-08-26 09:30:16');

-- --------------------------------------------------------

--
-- Table structure for table `tables`
--

CREATE TABLE IF NOT EXISTS `tables` (
  `id_table` int(30) NOT NULL AUTO_INCREMENT,
  `id_branch` int(30) NOT NULL,
  `table_type` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_table`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tables`
--

INSERT INTO `tables` (`id_table`, `id_branch`, `table_type`, `created_at`) VALUES
(1, 1, '1-2', '2016-08-15 09:55:07'),
(2, 1, '3-4', '2016-08-15 10:41:27'),
(3, 1, '6+', '2016-08-15 10:42:15');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(30) NOT NULL AUTO_INCREMENT,
  `username` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `first_name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `last_name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `gender` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `api_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `class` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `token` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `username`, `first_name`, `last_name`, `birthday`, `gender`, `email`, `password`, `api_key`, `class`, `token`, `created_at`) VALUES
(1, 'mimi', 'mimi', 'momo', '2536-07-21', 'male', 'mimi@email.com', '$2a$10$e2a3c66a34305b94234d2O7xD39Yj77aLtlFFQKGY7fV/g68U/Wia', 'd5a13ae23e4da496ee21a644bcd1f197', 'customer', '', '2016-06-20 22:38:32'),
(2, 'mimi2', 'mimi2', 'momo2', '2536-07-21', 'male', 'mimi2@email.com', '$2a$10$fcebe1ada942a2a84c8cduNM9KQ8w6Hd0YcPy3JBd43W6CKZrJRkq', '26034be3a160c1cc3801e6dac16c06ef', 'customer', '', '2016-06-22 02:32:32'),
(3, 'mimi3', 'mimi2', 'momo2', '2536-07-21', 'male', 'mimi3@email.com', '$2a$10$416742419aae8a391213duY10eb0V4vmjVxgsuNr0sQnMaVLjN/Nu', 'b59ad340df6261dd07197203951c0ccc', 'customer', '', '2016-06-23 11:32:43'),
(13, 'myshop', 'Assanee', 'Saksiritantawan', '2536-07-21', 'male', 'email@email.com', '$2a$10$f31be71823700dd85ecbauouJPIpQ3QPE2/CHY.b3z4zkQG0Oo.2i', 'a53e787b9b6d562e701e9a2a3446bfa7', 'shop', '', '2016-06-28 13:56:02'),
(14, 'shop1', 'Assanee', 'Saksiritantawan', '2536-07-21', 'male', 'shop1@email.com', '$2a$10$f591b778881982895dfc1eMKXsFg4.oh3zd56EhK0zU7z9JMysK6.', 'b6967f79be2600b05b4a7f82f31650b7', 'shop', '', '2016-08-15 10:06:16'),
(15, 'shop2', 'Assanee', 'Saksiritantawan', '2536-07-21', 'male', 'shop2@email.com', '$2a$10$d7b0ec70c3bda302be62cer5TxMbXnscboYu.DA4vrsS4Ywz32utS', '6e558f6d9760f7cbbbc6a5e0536de2b7', 'shop', '', '2016-08-15 10:06:24'),
(16, 'shop3', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop3@email.com', '$2a$10$16c6e674eb54ad5c5c371uLLRZ2TAYUlp6dfPHJkdyRUhAsEEVYcG', 'b83f6324c1de708982cee2ff9af57e3f', 'shop', '', '2016-08-15 10:06:43'),
(17, 'shop4', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop4@email.com', '$2a$10$19688b398c2be864e8a4fuLbjKIGY4vPy4reo9KLMFKVfNmhDqKIm', '26598087e4714e4a521e3ae35f85ebd4', 'shop', '', '2016-08-15 10:06:52'),
(18, 'shop5', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop5@email.com', '$2a$10$a97ec8bb0a413e14549e4uUTWz4WjY6JKdnFnuR67fM0lxUAEJv6u', 'ead8396352fc945d33e75d4e68b9fce7', 'shop', '', '2016-08-15 10:06:57'),
(19, 'shop6', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop6@email.com', '$2a$10$01618dd51603c3ddb4042uiwOciFShv7B9jSteMJ8E.3.dKRBMly.', '38e7d952ce07e1585c25120b0216b5c2', 'shop', '', '2016-08-15 10:07:03'),
(20, 'shop7', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop7@email.com', '$2a$10$c25509d89b4d538a05dacuGHMnNFvlY7PduNO7zBUQ/NRBP3nF9.q', '0dd1890229626fc7e043b48c2560a355', 'shop', '', '2016-08-15 10:07:14'),
(21, 'shop8', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop8@email.com', '$2a$10$56f11bc5785c5f4aabfe2uQiSDkcLnEFCLjvKyZZ//y1Fb.tsSyqW', '9ccce0568c36e5b70e3b7db04fb2f7d7', 'shop', '', '2016-08-15 10:07:20'),
(22, 'shop9', 'Assanee', 'Saksiritantawan', '1993-07-21', 'male', 'shop9@email.com', '$2a$10$c70a76bdd7afe7834622eu.UBkupoAegm4zqoICTyGDCYkg3he3jq', '78d0196a292bd7964b23c1283747afa7', 'shop', '', '2016-08-15 10:07:34'),
(23, 'myshop999', 'Assanee', 'Saksiritantawan', '2536-07-21', 'male', 'email999@email.com', '$2a$10$9e79e2a69194085bef1d3eJ2ytRqTjOIJi0GDd0P1C5Eh10vRFhrm', '5becb15464d44c9aba180840857e3011', 'shop', '', '2016-08-25 08:04:29'),
(24, 'myshop9997', 'Assanee', 'Saksiritantawan', '2016-12-12', 'male', 'email9997@email.com', '$2a$10$6d433c745b3e56259f34aOTpa2M6xIEDMpRHIaMsYRuryVvYciHgO', '44d9d413b96abd40105fd4ba698b7804', 'shop', '', '2016-08-25 08:05:07'),
(25, 'dfghsdfg', 'dfgsdfg', 'hjghjhjasdf', '2016-08-25', 'male', 'sdfsdfs@hsdht.com', '$2a$10$9a7896270334c7a189ba6eP6wADEsub8jy6y.mkQ5iqlycCXNlmBW', '57a389ff1e6ab70cd2d36d4e145142d5', 'customer', '', '2016-08-25 08:10:20');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
