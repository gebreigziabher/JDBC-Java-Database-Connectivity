
--
-- Database: `bank`
--
CREATE DATABASE IF NOT EXISTS `bank` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bank`;

-- --------------------------------------------------------

--
-- Table structure for table `tblaccount`
--

CREATE TABLE IF NOT EXISTS `tblaccount` (
  `acct_no` varchar(20) NOT NULL,
  `customer_name` varchar(30) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `branch` varchar(50) NOT NULL,
  `initial_balance` double NOT NULL,
  PRIMARY KEY (`acct_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblaccount`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `acct_no` varchar(20) NOT NULL,
  `customer_name` varchar(30) NOT NULL,
  `deposit` double NOT NULL,
  `withdraw` double NOT NULL,
  `balance` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

