/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - lifeline
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lifeline` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `lifeline`;

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `agent_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` char(10) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `agent` */

insert  into `agent`(`agent_id`,`login_id`,`first_name`,`last_name`,`gender`,`place`,`email`,`phone`) values 
(5,13,'Haris','V','male','Kalady','haris@gmail.com',8764398765),
(6,17,'hjhh','k k','female','Kozhikode','as@gmail.com',9876532199),
(8,26,'hkg','jmvb','male','ljkhkj','lkh@gmail.com',9887655432),
(9,27,'raj','kumar','male','jjn','ljknn@gamil.com',8998898998);

/*Table structure for table `assign` */

DROP TABLE IF EXISTS `assign`;

CREATE TABLE `assign` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `request_id` int(11) DEFAULT NULL,
  `status` varchar(200) NOT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

/*Data for the table `assign` */

insert  into `assign`(`assign_id`,`login_id`,`agent_id`,`request_id`,`status`) values 
(17,5,5,1,'pending'),
(18,5,6,1,'pending'),
(19,5,13,3,'pending'),
(20,5,13,4,'On the way'),
(21,5,13,5,'pending'),
(22,5,5,6,'pending'),
(23,5,5,4,'pending'),
(24,5,5,10,'pending'),
(25,5,19,3,'pending'),
(26,5,17,7,'completed'),
(27,5,19,5,'pending'),
(28,5,17,9,'completed'),
(29,5,17,13,'completed'),
(30,5,17,13,'completed'),
(31,5,13,8,'pending'),
(32,5,26,14,'pending'),
(33,5,17,11,'pending'),
(34,5,13,16,'pending'),
(35,5,13,17,'pending'),
(36,5,13,12,'pending'),
(37,5,13,19,'pending'),
(38,5,13,20,'pending'),
(39,5,13,21,'pending'),
(40,5,27,18,'pending'),
(41,28,27,22,'pending'),
(42,24,13,23,'pending'),
(43,24,27,15,'pending'),
(44,24,27,3,'pending');

/*Table structure for table `donor1` */

DROP TABLE IF EXISTS `donor1`;

CREATE TABLE `donor1` (
  `donor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(21) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`donor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `donor1` */

insert  into `donor1`(`donor_id`,`login_id`,`first_name`,`last_name`,`gender`,`dob`,`place`,`post`,`pin`,`email`,`phone`) values 
(4,5,'hhj','h','MALE','1997-03-05','pallikkal','pjasaja',123456,'ah@gmail.com',8796754432),
(6,14,'fdf','dfg','MALE','2000-12-12','fggf','fdf',123456,'ah@gmail.com',8796754432),
(7,15,'hlllll','dhhh','MALE','2000-12-05','hghj','pallikkal',121879,'as@gmail.com',9876432168),
(10,24,'ysh','ysh','MALE','1995-12-04','rgfedf','lkn',689898,'gigi@gm.com',9898989898),
(11,28,'Lohit','das','MALE','1995-02-08','jbkjb','jbkbn',696969,'jbh@gmail.com',9898989898);

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `feedback` varchar(200) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`login_id`,`feedback`,`date`) values 
(1,4,'Its nice','2222-02-03'),
(2,4,'ahha','2021-02-04'),
(3,4,'ahha','2021-02-04'),
(4,4,'ahha','2021-02-04'),
(5,4,'ahha','2021-02-04'),
(6,4,'ahha','2021-02-04'),
(7,4,'hooo','2021-02-04'),
(8,4,'hooo','2021-02-04'),
(9,4,'hj','2021-02-04'),
(10,4,'hj','2021-02-04'),
(11,4,'hi','2021-02-04'),
(12,4,'hi','2021-02-04'),
(13,4,'ififf','2021-02-04'),
(14,4,'vbb','2021-02-04'),
(15,4,'vbb','2021-02-04'),
(16,4,'vbb','2021-02-04'),
(17,4,'vbb','2021-02-04'),
(18,4,'idiritkfi','2021-02-04'),
(19,4,'fghf','2021-02-05'),
(20,4,'gshs','2021-02-07'),
(21,4,'Hi','2021-02-09'),
(22,23,'hi','2021-02-09'),
(23,37,'nice','2021-02-21'),
(24,37,'nice','2021-02-21'),
(25,40,'good','2022-03-21');

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `item_type` varchar(30) DEFAULT NULL,
  `item_name` varchar(30) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `item` */

insert  into `item`(`item_id`,`login_id`,`item_type`,`item_name`,`quantity`) values 
(7,5,'Food','Ragi',559),
(8,5,'Food','Fruits',235),
(9,18,'Cloths','Linen shirt',500),
(10,24,'Cloths','Rice',500),
(11,5,'Cloths','Cotton Saree',200),
(12,5,'Food','hgbmbv',22),
(14,24,'Cloths','fvjggj',4);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `Login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Login_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`Login_id`,`username`,`password`,`user_type`) values 
(1,'admin','admin','admin'),
(4,'Anu12','anu12','block'),
(5,'hhj12','12356789','block'),
(13,'Haris136','haris135','agent'),
(14,'asd','asdfghjk','donor'),
(15,'as23','996153783','donor'),
(17,'as3','12356789','agent'),
(23,'ysh','123456','user'),
(24,'yshnav','12356789','donor'),
(25,'','',NULL),
(26,'yash1234','123456789','agent'),
(27,'b','b','agent'),
(28,'vaish','123456789','donor'),
(37,'a','a','user'),
(40,'anu','123456','user');

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `assign_id` int(11) DEFAULT NULL,
  `report` varchar(200) NOT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `report` */

insert  into `report`(`report_id`,`login_id`,`date`,`assign_id`,`report`,`agent_id`,`item_id`,`user_id`) values 
(13,5,'2020-11-06',17,'Thesis.pdf',5,7,1),
(14,5,'2020-11-09',0,'NTA-NET-Computer-Science-Paper-2-Nov-2017.pdf',0,0,0),
(15,5,'2020-11-09',18,'ACFrOgB8L2CZ7fmy1OYZmR2IiROGjHYOn-8_YQCDB24fnDGJdQ_rgeDm19hkFoRn5tMD7SHjb4Rpuj9vfVofx63lXL2gEC2-Xm2NbTX_WDNr4vJ9KhA2OkG6GkpaA9w.pdf',6,7,1),
(16,5,'2021-02-09',17,'valid.txt',5,7,1),
(18,5,'2021-02-21',30,'app-debug.apk',6,7,5);

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `donor_id` int(11) DEFAULT NULL,
  `status` varchar(222) DEFAULT NULL,
  `date` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`login_id`,`item_id`,`description`,`donor_id`,`status`,`date`) values 
(1,1,7,'fhdjj',4,'pending','2021-02-08'),
(2,1,8,'hkjllll',6,'pending','2021-02-08'),
(3,4,8,'description',5,'assigned','2021-02-08'),
(4,4,7,'description',5,'pending','2021-02-08'),
(5,4,7,'description',5,'pending','2021-02-23'),
(6,4,7,'r rbrb',5,'assigned','2021-02-08'),
(7,4,8,'t tnt',5,'assigned','2021-02-08'),
(8,23,9,'need 2 no.s of shirts',18,'assigned','2021-02-09'),
(9,4,8,'need 2',5,'pending','2021-02-09'),
(10,4,7,'Need 1 kg',5,'pending','2021-02-09'),
(11,23,9,'1 shirt',18,'assigned','2021-02-09'),
(12,23,8,'hs',5,'assigned','2021-02-09'),
(13,23,7,'dh',5,'assigned','2021-02-09'),
(14,23,7,'sh',5,'assigned','2021-02-09'),
(15,23,9,'hs',18,'assigned','2021-02-09'),
(16,23,10,'hih',24,'assigned','2021-02-10'),
(17,23,7,'vz',5,'assigned','2021-02-10'),
(18,23,8,'vs',5,'assigned','2021-02-10'),
(19,23,9,'sh',18,'assigned','2021-02-10'),
(20,23,10,'s',24,'assigned','2021-02-10'),
(21,23,11,'2',5,'assigned','2021-02-10'),
(22,37,8,'apple,orange',5,'assigned','2021-02-21'),
(23,37,8,'apple,orange',5,'compleated','2021-02-21'),
(24,40,7,'ggh',5,'pending','2022-03-21');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `phone` bigint(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`first_name`,`last_name`,`place`,`post`,`pin`,`district`,`phone`,`email`) values 
(1,4,'Anu','kk','Mannarkkad','Mannarkkad',678387,'Malappuram',987653217,'anu@gmail.com'),
(5,23,'vaishnav','ajay','vengara','vengara',676304,'Malappuram',9847771696,'vvcvaishnav@gmail.com'),
(6,30,'gsgd','iwheh','hss','zjhz',646464,'ggdgsg',9494949494,'vvcvaishnav@gmail.com'),
(7,31,'yduddu','jduduxu','ifuddux','ncjjxuxu',656265,'jxucuxuc',8675757755,'riitvaish@gmail.com'),
(8,32,'Anu','anu','isis','vsosivi',646464,'kvskvsk',6464646464,'ksvskv@gmail.com'),
(9,37,'bshd','shs','hssh','hssj',664646,'sggsgs',9969352536,'riitvaish@gmail.com'),
(10,40,'anu','m','clt','clt',679632,'kzkd',9693969396,'anu@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
