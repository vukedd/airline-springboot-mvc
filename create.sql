USE Airline2024;

CREATE TABLE Location (
	LocationId bigint PRIMARY KEY AUTO_INCREMENT,
    Country varchar(30),
    City varchar(30),
    Continent smallint
);

CREATE TABLE Airport (
	AirportId bigint PRIMARY KEY AUTO_INCREMENT,
    AirportCode varchar(10) UNIQUE,
    LocationId bigint,
    
    FOREIGN KEY (LocationId) REFERENCES Location(LocationId)
);

CREATE TABLE Airplane (
	AirplaneId bigint PRIMARY KEY AUTO_INCREMENT,
    Name varchar(30),
    NumberOfColumns smallint,
    NumberOfRows smallint
);

CREATE TABLE Flight (
	FlightId bigint PRIMARY KEY AUTO_INCREMENT,
    DateOfDeparture date,
    Duration smallint,
    TicketPrice double,
    DepartureId bigint,
    DestinationId bigint,
    AirplaneId bigint,
    
    FOREIGN KEY (DepartureId) REFERENCES Airport(AirportId),
    FOREIGN KEY (DestinationId) REFERENCES Airport(AirportId),
    FOREIGN KEY (AirplaneId) REFERENCES Airplane(AirplaneId)
);

CREATE TABLE Reservation (
	ReservationId bigint PRIMARY KEY AUTO_INCREMENT,
    ReservationDate date,
    TotalPrice double
);

CREATE TABLE Ticket (
	TicketId bigint PRIMARY KEY AUTO_INCREMENT,
    SeatRow smallint,
    SeatColumn smallint,
    PassportNumber varchar(10),
    FirstName varchar(30),
    LastName varchar(30),
    FlightId bigint,
    ReservationId bigint,
    
    FOREIGN KEY (ReservationId) REFERENCES Reservation(ReservationId),
    FOREIGN KEY (FlightId) REFERENCES Flight(FlightId)
);

CREATE TABLE LoyaltyCard (
	LoyaltyCardId bigint PRIMARY KEY AUTO_INCREMENT,
    Points double
);

CREATE TABLE User (
	UserId bigint PRIMARY KEY AUTO_INCREMENT,
	Username varchar(15) unique,
    Password varchar(20),
    Email varchar(30) unique,
    FirstName varchar(30),
    LastName varchar(30),
    DateOfBirth date,
    DateOfRegistration date,
    Role tinyint,
    LoyaltyCardId bigint null,
    
    FOREIGN KEY (LoyaltyCardId) REFERENCES LoyaltyCard(LoyaltyCardId)
);

CREATE TABLE LoyaltyCardRequest	(
	RequestId bigint PRIMARY KEY AUTO_INCREMENT,
    Status tinyInt,
    UserId bigint,
    
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);
