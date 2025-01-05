-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2025 at 03:51 PM
-- Server version: 10.4.32-MariaDB-log
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_kost`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_info`
--

CREATE TABLE `admin_info` (
  `Owner_Name` varchar(100) NOT NULL,
  `Kost_Address` text NOT NULL,
  `Account_Number` varchar(20) DEFAULT NULL,
  `Account_Name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `ID_Transaction` int(11) NOT NULL,
  `ID_Tenant` int(11) DEFAULT NULL,
  `Name` varchar(100) NOT NULL,
  `ID_Room` int(11) DEFAULT NULL,
  `Room_Price` int(50) DEFAULT NULL,
  `Payment_Date` date DEFAULT NULL,
  `Amount_Paid` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Role` enum('Admin','Tenant') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `ID_Room` int(11) NOT NULL,
  `ID_Tenant` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Message` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room_info`
--

CREATE TABLE `room_info` (
  `ID_Room` int(11) NOT NULL,
  `Facilities` text DEFAULT NULL,
  `Size` varchar(50) NOT NULL,
  `Room_Price` int(50) NOT NULL,
  `Photo_Upload` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tenant_accounts`
--

CREATE TABLE `tenant_accounts` (
  `ID_Tenant` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tenant_info`
--

CREATE TABLE `tenant_info` (
  `ID_Tenant` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Phone_Number` varchar(15) NOT NULL,
  `Address` text DEFAULT NULL,
  `ID_Room` int(11) DEFAULT NULL,
  `Room_Price` int(50) DEFAULT NULL,
  `CheckIn_Date` date DEFAULT NULL,
  `CheckOut_Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tenant_payments`
--

CREATE TABLE `tenant_payments` (
  `ID_Tenant` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Payment_Proof` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`ID_Transaction`),
  ADD KEY `ID_Tenant` (`ID_Tenant`),
  ADD KEY `ID_Room` (`ID_Room`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`ID_Room`,`ID_Tenant`),
  ADD KEY `ID_Tenant` (`ID_Tenant`);

--
-- Indexes for table `room_info`
--
ALTER TABLE `room_info`
  ADD PRIMARY KEY (`ID_Room`);

--
-- Indexes for table `tenant_accounts`
--
ALTER TABLE `tenant_accounts`
  ADD PRIMARY KEY (`ID_Tenant`);

--
-- Indexes for table `tenant_info`
--
ALTER TABLE `tenant_info`
  ADD PRIMARY KEY (`ID_Tenant`),
  ADD KEY `ID_Room` (`ID_Room`);

--
-- Indexes for table `tenant_payments`
--
ALTER TABLE `tenant_payments`
  ADD PRIMARY KEY (`ID_Tenant`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `ID_Transaction` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `room_info`
--
ALTER TABLE `room_info`
  MODIFY `ID_Room` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tenant_info`
--
ALTER TABLE `tenant_info`
  MODIFY `ID_Tenant` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`ID_Tenant`) REFERENCES `tenant_info` (`ID_Tenant`),
  ADD CONSTRAINT `bills_ibfk_2` FOREIGN KEY (`ID_Room`) REFERENCES `room_info` (`ID_Room`);

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`ID_Room`) REFERENCES `room_info` (`ID_Room`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`ID_Tenant`) REFERENCES `tenant_info` (`ID_Tenant`);

--
-- Constraints for table `tenant_accounts`
--
ALTER TABLE `tenant_accounts`
  ADD CONSTRAINT `tenant_accounts_ibfk_1` FOREIGN KEY (`ID_Tenant`) REFERENCES `tenant_info` (`ID_Tenant`);

--
-- Constraints for table `tenant_info`
--
ALTER TABLE `tenant_info`
  ADD CONSTRAINT `tenant_info_ibfk_1` FOREIGN KEY (`ID_Room`) REFERENCES `room_info` (`ID_Room`);

--
-- Constraints for table `tenant_payments`
--
ALTER TABLE `tenant_payments`
  ADD CONSTRAINT `tenant_payments_ibfk_1` FOREIGN KEY (`ID_Tenant`) REFERENCES `tenant_info` (`ID_Tenant`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
