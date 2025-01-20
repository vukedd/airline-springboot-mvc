CREATE DATABASE Airline2024;

CREATE TABLE `location` (
  `LocationId` bigint NOT NULL AUTO_INCREMENT,
  `Country` varchar(30) DEFAULT NULL,
  `City` varchar(30) DEFAULT NULL,
  `Continent` smallint DEFAULT NULL,
  `LocationImage` longblob,
  PRIMARY KEY (`LocationId`)
);

CREATE TABLE `airport` (
  `AirportId` bigint NOT NULL AUTO_INCREMENT,
  `AirportCode` varchar(10) DEFAULT NULL,
  `LocationId` bigint DEFAULT NULL,
  PRIMARY KEY (`AirportId`),
  UNIQUE KEY `AirportCode` (`AirportCode`),
  KEY `LocationId` (`LocationId`),
  CONSTRAINT `airport_ibfk_1` FOREIGN KEY (`LocationId`) REFERENCES `location` (`LocationId`)
);

CREATE TABLE `airplane` (
  `AirplaneId` bigint NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  `NumberOfColumns` smallint DEFAULT NULL,
  `NumberOfRows` smallint DEFAULT NULL,
  PRIMARY KEY (`AirplaneId`)
);

CREATE TABLE `flight` (
  `FlightId` bigint NOT NULL AUTO_INCREMENT,
  `DateOfDeparture` datetime DEFAULT NULL,
  `Duration` smallint DEFAULT NULL,
  `TicketPrice` double DEFAULT NULL,
  `DepartureId` bigint DEFAULT NULL,
  `DestinationId` bigint DEFAULT NULL,
  `AirplaneId` bigint DEFAULT NULL,
  `IsCancelled` smallint DEFAULT NULL,
  PRIMARY KEY (`FlightId`),
  KEY `DepartureId` (`DepartureId`),
  KEY `DestinationId` (`DestinationId`),
  KEY `AirplaneId` (`AirplaneId`),
  CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`DepartureId`) REFERENCES `airport` (`AirportId`),
  CONSTRAINT `flight_ibfk_2` FOREIGN KEY (`DestinationId`) REFERENCES `airport` (`AirportId`),
  CONSTRAINT `flight_ibfk_3` FOREIGN KEY (`AirplaneId`) REFERENCES `airplane` (`AirplaneId`)
);

CREATE TABLE `loyaltycard` (
  `LoyaltyCardId` bigint NOT NULL AUTO_INCREMENT,
  `Points` double DEFAULT NULL,
  PRIMARY KEY (`LoyaltyCardId`)
);

CREATE TABLE `user` (
  `UserId` bigint NOT NULL AUTO_INCREMENT,
  `Username` varchar(15) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `FirstName` varchar(30) DEFAULT NULL,
  `LastName` varchar(30) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `DateOfRegistration` date DEFAULT NULL,
  `Role` tinyint DEFAULT NULL,
  `LoyaltyCardId` bigint DEFAULT NULL,
  `IsBlocked` smallint DEFAULT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `Email` (`Email`),
  KEY `LoyaltyCardId` (`LoyaltyCardId`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`LoyaltyCardId`) REFERENCES `loyaltycard` (`LoyaltyCardId`)
);

CREATE TABLE `loyaltycardrequest` (
  `RequestId` bigint NOT NULL AUTO_INCREMENT,
  `Status` tinyint DEFAULT NULL,
  `UserId` bigint DEFAULT NULL,
  PRIMARY KEY (`RequestId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `loyaltycardrequest_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`)
);

CREATE TABLE `reservation` (
  `ReservationId` bigint NOT NULL AUTO_INCREMENT,
  `ReservationDate` date DEFAULT NULL,
  `TotalPrice` double DEFAULT NULL,
  `UserId` bigint DEFAULT NULL,
  PRIMARY KEY (`ReservationId`),
  KEY `fk_reservation_user` (`UserId`),
  CONSTRAINT `fk_reservation_user` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`)
);

CREATE TABLE `ticket` (
  `TicketId` bigint NOT NULL AUTO_INCREMENT,
  `SeatRow` smallint DEFAULT NULL,
  `SeatColumn` smallint DEFAULT NULL,
  `PassportNumber` varchar(10) DEFAULT NULL,
  `FirstName` varchar(30) DEFAULT NULL,
  `LastName` varchar(30) DEFAULT NULL,
  `FlightId` bigint DEFAULT NULL,
  `ReservationId` bigint DEFAULT NULL,
  PRIMARY KEY (`TicketId`),
  KEY `ReservationId` (`ReservationId`),
  KEY `FlightId` (`FlightId`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`ReservationId`) REFERENCES `reservation` (`ReservationId`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`FlightId`) REFERENCES `flight` (`FlightId`)
);

CREATE TABLE `discount` (
  `DiscountId` bigint NOT NULL AUTO_INCREMENT,
  `DiscountPercentage` double DEFAULT NULL,
  `DiscountValidDate` date DEFAULT NULL,
  `FlightId` bigint DEFAULT NULL,
  PRIMARY KEY (`DiscountId`),
  KEY `fk_discount_flight` (`FlightId`),
  CONSTRAINT `fk_discount_flight` FOREIGN KEY (`FlightId`) REFERENCES `flight` (`FlightId`)
);

CREATE TABLE `flightcancelation` (
  `FlightCancelationId` bigint NOT NULL AUTO_INCREMENT,
  `CancelationReason` text,
  `FlightId` bigint DEFAULT NULL,
  PRIMARY KEY (`FlightCancelationId`),
  KEY `fk_flight_cancelation` (`FlightId`),
  CONSTRAINT `fk_flight_cancelation` FOREIGN KEY (`FlightId`) REFERENCES `flight` (`FlightId`)
);

CREATE TABLE `wishlist` (
  `WishlistId` bigint NOT NULL AUTO_INCREMENT,
  `UserId` bigint DEFAULT NULL,
  PRIMARY KEY (`WishlistId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`)
);

CREATE TABLE `wishlistitem` (
  `WishlistItemId` bigint NOT NULL AUTO_INCREMENT,
  `FlightId` bigint DEFAULT NULL,
  `WishlistId` bigint DEFAULT NULL,
  PRIMARY KEY (`WishlistItemId`),
  KEY `FlightId` (`FlightId`),
  KEY `WishlistId` (`WishlistId`),
  CONSTRAINT `wishlistitem_ibfk_1` FOREIGN KEY (`FlightId`) REFERENCES `flight` (`FlightId`),
  CONSTRAINT `wishlistitem_ibfk_2` FOREIGN KEY (`WishlistId`) REFERENCES `wishlist` (`WishlistId`)
);