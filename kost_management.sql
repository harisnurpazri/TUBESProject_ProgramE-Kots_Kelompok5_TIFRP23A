-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 09, 2025 at 09:23 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kost_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_info`
--

CREATE TABLE `admin_info` (
  `ID_Admin` int NOT NULL,
  `Kost_Address` text NOT NULL,
  `Account_Number` varchar(20) NOT NULL,
  `Account_Name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `ID_Transaction` int NOT NULL,
  `ID_Tenant` int DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `ID_Room` int DEFAULT NULL,
  `Room_Price` decimal(10,2) DEFAULT NULL,
  `Payment_Date` date DEFAULT NULL,
  `Amount_Paid` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `ID_Account` int NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` enum('Admin','Tenant') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`ID_Account`, `Name`, `Username`, `Password`, `Role`) VALUES
(1, 'Admin', 'admin', 'admin123', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `ID_Message` int NOT NULL,
  `ID_Tenant` int DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Message` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room_info`
--

CREATE TABLE `room_info` (
  `ID_Room` int NOT NULL,
  `Facilities` text,
  `Size` varchar(50) DEFAULT NULL,
  `Room_Price` decimal(10,2) DEFAULT NULL,
  `Status` enum('Kosong','Isi') DEFAULT 'Kosong'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_info`
--

INSERT INTO `room_info` (`ID_Room`, `Facilities`, `Size`, `Room_Price`, `Status`) VALUES
(1, 'AC, Kamar Mandi Dalam', '2x3', 500000.00, 'Kosong'),
(2, 'AC, Kamar Mandi Luar', '2x2', 450000.00, 'Kosong'),
(3, 'Kamar Mandi Dalam', '2x3', 400000.00, 'Kosong'),
(4, 'Kamar Mandi Luar', '2x2', 350000.00, 'Kosong'),
(5, 'AC, Kamar Mandi Dalam', '3x3', 600000.00, 'Kosong');

-- --------------------------------------------------------

--
-- Table structure for table `tenant_info`
--

CREATE TABLE `tenant_info` (
  `ID_Tenant` int NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Phone_Number` varchar(15) NOT NULL,
  `Address` text,
  `ID_Room` int DEFAULT NULL,
  `Room_Price` decimal(10,2) DEFAULT NULL,
  `Check_In_Date` date DEFAULT NULL,
  `Pay_Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_info`
--
ALTER TABLE `admin_info`
  ADD PRIMARY KEY (`ID_Admin`);

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
  ADD PRIMARY KEY (`ID_Account`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`ID_Message`),
  ADD KEY `ID_Tenant` (`ID_Tenant`);

--
-- Indexes for table `room_info`
--
ALTER TABLE `room_info`
  ADD PRIMARY KEY (`ID_Room`);

--
-- Indexes for table `tenant_info`
--
ALTER TABLE `tenant_info`
  ADD PRIMARY KEY (`ID_Tenant`),
  ADD KEY `ID_Room` (`ID_Room`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_info`
--
ALTER TABLE `admin_info`
  MODIFY `ID_Admin` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `ID_Transaction` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `ID_Account` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `ID_Message` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `room_info`
--
ALTER TABLE `room_info`
  MODIFY `ID_Room` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tenant_info`
--
ALTER TABLE `tenant_info`
  MODIFY `ID_Tenant` int NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`ID_Tenant`) REFERENCES `tenant_info` (`ID_Tenant`);

--
-- Constraints for table `tenant_info`
--
ALTER TABLE `tenant_info`
  ADD CONSTRAINT `tenant_info_ibfk_1` FOREIGN KEY (`ID_Room`) REFERENCES `room_info` (`ID_Room`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
