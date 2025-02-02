CREATE DATABASE IF NOT EXISTS Airline2024;
USE Airline2024;

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
  PRIMARY KEY (`airplaneId`)
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


INSERT INTO `user` (Username, Password, Email, FirstName, LastName, DateOfBirth, DateOfRegistration, Role, LoyaltyCardId,IsBlocked) 
VALUES ('admin', 'admin', 'admin@mail.com', 'admin', 'admin', '2000-01-01', '2025-01-30', 0, NULL, 0);

INSERT INTO `location` (`Country`, `City`, `Continent`, `LocationImage`) VALUES
('United States', 'New York', 5, NULL),
('United Kingdom', 'London', 0, NULL),
('Japan', 'Tokyo', 1, NULL),
('Brazil', 'Rio de Janeiro', 3, NULL),
('Australia', 'Sydney', 6, NULL),
("UAE", "Dubai", 1, NULL),
("China", "Guangzho", 1, NULL),
("China", "Hong Kong", 1, NULL);

INSERT INTO `airport` (`AirportCode`, `LocationId`) VALUES
('JFK', 1),
('LHR', 2),
('HND', 3),
('GIG', 4),
('SYD', 5),
("DXB", 6),
('CAN', 7),
('HKG', 8);

INSERT INTO `airplane` (`Name`, `NumberOfColumns`, `NumberOfRows`) VALUES
('Boeing 737', 6, 30),
('Airbus A320', 6, 25),
('Boeing 777', 9, 40);

INSERT INTO `flight` (`DateOfDeparture`, `Duration`, `TicketPrice`, `DepartureId`, `DestinationId`, `AirplaneId`, `IsCancelled`) VALUES
('2025-02-15 08:00:00', 360, 500.00, 1, 2, 1, 0),
('2025-03-10 15:00:00', 720, 800.00, 2, 3, 2, 0),
('2025-04-05 12:30:00', 540, 650.00, 3, 4, 3, 1);

INSERT INTO `loyaltycard` (`Points`) VALUES
(100.00),
(250.50),
(0.00);

INSERT INTO `user` (`Username`, `Password`, `Email`, `FirstName`, `LastName`, `DateOfBirth`, `DateOfRegistration`, `Role`, `LoyaltyCardId`, `IsBlocked`) VALUES
('john_doe', 'password1', 'john@example.com', 'John', 'Doe', '1990-05-15', '2025-01-05', 1, 1, 0),
('jane_doe', 'password2', 'jane@example.com', 'Jane', 'Doe', '1992-08-20', '2025-01-10', 1, 2, 0);

INSERT INTO `reservation` (`ReservationDate`, `TotalPrice`, `UserId`) VALUES
('2025-02-01', 500.00, 2),
('2025-02-02', 1600.00, 3);

INSERT INTO `ticket` (`SeatRow`, `SeatColumn`, `PassportNumber`, `FirstName`, `LastName`, `FlightId`, `ReservationId`) VALUES
(5, 2, 'A12345678', 'John', 'Doe', 1, 1),
(10, 4, 'B98765432', 'Jane', 'Doe', 2, 2);

INSERT INTO `discount` (`DiscountPercentage`, `DiscountValidDate`, `FlightId`) VALUES
(10.00, '2025-02-10', 1),
(15.00, '2025-03-05', 2);

INSERT INTO `flightcancelation` (`CancelationReason`, `FlightId`) VALUES
('Weather conditions', 3);

INSERT INTO `flight` (`DateOfDeparture`, `Duration`, `TicketPrice`, `DepartureId`, `DestinationId`, `AirplaneId`, `IsCancelled`) VALUES
('2025-05-01 06:00:00', 300, 450.00, 4, 5, 1, 0),
('2025-06-15 14:00:00', 600, 750.00, 1, 4, 2, 0),
('2025-07-20 09:30:00', 480, 700.00, 3, 1, 3, 0);

INSERT INTO `reservation` (`ReservationDate`, `TotalPrice`, `UserId`) VALUES
('2025-03-01', 900.00, 2),
('2025-04-10', 1200.00, 3),
('2025-05-15', 750.00, 2);

INSERT INTO `ticket` (`SeatRow`, `SeatColumn`, `PassportNumber`, `FirstName`, `LastName`, `FlightId`, `ReservationId`) VALUES
(2, 1, 'C98712345', 'John', 'Doe', 4, 3),
(15, 5, 'D12398765', 'Jane', 'Doe', 5, 4),
(8, 3, 'E56473829', 'John', 'Doe', 6, 5);

INSERT INTO `wishlist` (`UserId`) VALUES
(2), 
(3); 

INSERT INTO `wishlistitem` (`FlightId`, `WishlistId`) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 1),
(6, 2);

INSERT INTO `flight` (`DateOfDeparture`, `Duration`, `TicketPrice`, `DepartureId`, `DestinationId`, `AirplaneId`, `IsCancelled`) VALUES
('2025-02-15 10:00:00', 360, 4000.00, 1, 6, 1, 0),
('2025-02-15 14:30:00', 420, 4500.00, 1, 2, 2, 0), 
('2025-02-15 18:00:00', 500, 6000.00, 1, 5, 3, 0),

('2025-02-15 16:00:00', 720, 7000.00, 6, 3, 2, 0),
('2025-02-15 20:00:00', 660, 7500.00, 2, 3, 3, 0),
('2025-02-16 07:00:00', 900, 8000.00, 5, 3, 1, 0),

('2025-02-15 12:00:00', 360, 5000.00, 1, 7, 2, 0),
('2025-02-15 18:00:00', 180, 3000.00, 7, 8, 1, 0),
('2025-02-15 22:00:00', 240, 4000.00, 8, 3, 3, 0);