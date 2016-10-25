-- phpMyAdmin SQL Dump
-- version 3.5.3
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2016 年 10 月 25 日 01:42
-- 服务器版本: 5.5.19
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `cloudlive`
--

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `accountID` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `lived_count` int(11) NOT NULL,
  `fans_count` int(11) NOT NULL,
  `fav_count` int(11) NOT NULL,
  `money_count` int(11) NOT NULL,
  `grade_count` int(11) NOT NULL,
  `isLiving` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `userName`, `pwd`, `accountID`, `user_icon`, `lived_count`, `fans_count`, `fav_count`, `money_count`, `grade_count`, `isLiving`) VALUES
(5, 'Xidada', '8888', '44732566', 'http://', 0, 0, 0, 0, 1, 0),
(6, 'heihei', '8888', '20117479', 'http://', 0, 0, 0, 0, 1, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
