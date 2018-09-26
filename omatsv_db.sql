-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2018 at 06:00 AM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `omatsv_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `genre` text NOT NULL,
  `language` varchar(100) NOT NULL,
  `cast` text NOT NULL,
  `synopsis` text NOT NULL,
  `rating` float(2,2) NOT NULL,
  `writers` text NOT NULL,
  `directed_by` varchar(100) NOT NULL,
  `runtime` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `uname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mobile` text NOT NULL,
  `password` varchar(50) NOT NULL,
  `country` text NOT NULL,
  `gender` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`uname`, `email`, `mobile`, `password`, `country`, `gender`) VALUES
('Fahina', 'fa@mail.com', '3246245625', '3456', 'BD', 'F'),
('sagor123', 'sa@mail.com', '017xxxxxxxxxxx', '12345', 'USA', 'M'),
('sam23', 'sam@email.com', '016xxxxxxxxx', '2345', 'BANGLADESH', 'M');

-- --------------------------------------------------------

--
-- Table structure for table `tv_shows`
--

CREATE TABLE `tv_shows` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `genre` text NOT NULL,
  `language` varchar(100) NOT NULL,
  `cast` text NOT NULL,
  `synopsis` text NOT NULL,
  `rating` float(2,2) NOT NULL,
  `season` varchar(100) NOT NULL,
  `season_no` int(100) NOT NULL,
  `episode` varchar(100) NOT NULL,
  `episode_no` int(200) NOT NULL,
  `creator` varchar(100) NOT NULL,
  `runtime` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`uname`);

--
-- Indexes for table `tv_shows`
--
ALTER TABLE `tv_shows`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tv_shows`
--
ALTER TABLE `tv_shows`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
