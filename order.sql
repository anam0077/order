-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 08, 2023 at 03:21 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `order`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerId` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerId`, `name`) VALUES
('161ff71c-374a-4cc1-ada8-c4906d2df613', 'Anam'),
('96606be0-5e70-4d64-83bc-1fafebc74452', 'Septian');

-- --------------------------------------------------------

--
-- Table structure for table `orderlist`
--

CREATE TABLE `orderlist` (
  `no` int(11) NOT NULL,
  `customerId` varchar(256) NOT NULL,
  `product` varchar(256) NOT NULL,
  `amount` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderlist`
--

INSERT INTO `orderlist` (`no`, `customerId`, `product`, `amount`) VALUES
(8, '96606be0-5e70-4d64-83bc-1fafebc74452', '17f6f068-d55d-4850-b67e-fe2e930f6c91', 1),
(9, '96606be0-5e70-4d64-83bc-1fafebc74452', 'ec11aab9-5273-43f5-b6b8-84cbbdd53f3f', 1),
(10, '96606be0-5e70-4d64-83bc-1fafebc74452', '676c8a04-7d5e-4bb1-9a33-bba7daccee78', 1),
(11, '161ff71c-374a-4cc1-ada8-c4906d2df613', '17f6f068-d55d-4850-b67e-fe2e930f6c91', 1),
(12, '161ff71c-374a-4cc1-ada8-c4906d2df613', 'ec11aab9-5273-43f5-b6b8-84cbbdd53f3f', 1),
(13, '161ff71c-374a-4cc1-ada8-c4906d2df613', '676c8a04-7d5e-4bb1-9a33-bba7daccee78', 1),
(14, '161ff71c-374a-4cc1-ada8-c4906d2df613', '17f6f068-d55d-4850-b67e-fe2e930f6c91', 1),
(15, '161ff71c-374a-4cc1-ada8-c4906d2df613', 'ec11aab9-5273-43f5-b6b8-84cbbdd53f3f', 1),
(16, '161ff71c-374a-4cc1-ada8-c4906d2df613', '676c8a04-7d5e-4bb1-9a33-bba7daccee78', 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderqueue`
--

CREATE TABLE `orderqueue` (
  `queueNo` int(10) NOT NULL,
  `customerId` varchar(256) NOT NULL,
  `status` varchar(30) NOT NULL,
  `orderAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderqueue`
--

INSERT INTO `orderqueue` (`queueNo`, `customerId`, `status`, `orderAt`) VALUES
(1, '96606be0-5e70-4d64-83bc-1fafebc74452', 'process', '2023-11-07 08:44:15'),
(2, '161ff71c-374a-4cc1-ada8-c4906d2df613', 'process', '2023-11-07 08:45:25'),
(3, '161ff71c-374a-4cc1-ada8-c4906d2df613', 'process', '2023-11-07 17:42:24');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` int(255) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productId`, `name`, `description`, `price`, `createdAt`) VALUES
('79c59c25-dabd-4c1b-857d-b11b2ef29505', 'ayam', 'dada / paha goreng', 15000, '2023-11-07 08:37:38'),
('f3a2acb0-25bc-4ee5-9d3b-0c4109c0d86b', 'ayam', 'dada / paha bakar', 18000, '2023-11-07 08:37:49'),
('ec11aab9-5273-43f5-b6b8-84cbbdd53f3f', 'lele', 'goreng', 11000, '2023-11-07 08:38:27'),
('87d2ee5f-74ac-4b0e-88f1-cdb7ef96cd0b', 'Es teh', 'Tawar', 2000, '2023-11-07 08:39:05'),
('17f6f068-d55d-4850-b67e-fe2e930f6c91', 'Es teh', 'Manis', 3000, '2023-11-07 08:39:18'),
('6d067579-658d-4009-8680-30910e203936', 'Es Jeruk', ' ', 5000, '2023-11-07 08:39:36'),
('676c8a04-7d5e-4bb1-9a33-bba7daccee78', 'Nasi ', 'Uduk', 7000, '2023-11-07 08:43:29'),
('fc7e4a38-75dd-491b-9a61-d34b437659bf', 'Nasi ', 'putih', 5000, '2023-11-07 08:43:39');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD UNIQUE KEY `customerId` (`customerId`);

--
-- Indexes for table `orderlist`
--
ALTER TABLE `orderlist`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD UNIQUE KEY `productId` (`productId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orderlist`
--
ALTER TABLE `orderlist`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
