create database mydb1;
use mydb1;

create TABLE Users1 (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
select *from Users1
alter table Users1
Drop column Email;
alter table Users1
Drop column CreatedAt;
alter table Users1
Drop column UpdatedAt;
-- Table for Booking details
CREATE TABLE IF NOT EXISTS Trains (
    TrainID INT PRIMARY KEY AUTO_INCREMENT,
    TrainName VARCHAR(100) NOT NULL,
    Source VARCHAR(100) NOT NULL,
    Destination VARCHAR(100) NOT NULL,
    TotalSeats INT NOT NULL,
    AvailableSeats INT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
select*
from Trains;


SELECT * FROM Users1;
Drop Table Tickets
CREATE TABLE IF NOT EXISTS Tickets (
    TicketID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    TrainID INT NOT NULL,
    SeatNumber INT NOT NULL,
    BookingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status ENUM('Booked', 'Cancelled') DEFAULT 'Booked',
    FOREIGN KEY (UserID) REFERENCES Users1(UserID),
    FOREIGN KEY (TrainID) REFERENCES Trains(TrainID),
    CONSTRAINT UniqueSeat UNIQUE (TrainID, SeatNumber)
);
Select *
From Tickets;

CREATE TABLE RailwayCustomers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    contact_number VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    address TEXT NOT NULL,
    id_proof VARCHAR(100) NOT NULL,
    registered_date DATE NOT NULL,
    last_travel_date DATE DEFAULT NULL
);
Select *
From RailwayCustomers;
CREATE TABLE RailwayStations (
    station_id INT PRIMARY KEY AUTO_INCREMENT,
    station_code VARCHAR(10) NOT NULL UNIQUE,     
    station_name VARCHAR(100) NOT NULL,            
    city VARCHAR(100) NOT NULL,                    
    state VARCHAR(100) NOT NULL,                   
    pincode VARCHAR(10),                           
    platforms INT NOT NULL,                      
    has_waiting_room BOOLEAN DEFAULT FALSE,       
    has_food_court BOOLEAN DEFAULT FALSE,          
    has_wifi BOOLEAN DEFAULT FALSE,                
    contact_number VARCHAR(15),                    
    last_renovation DATE DEFAULT NULL              
);
Select *
From RailwayStations;

CREATE TABLE Coaches (
    coach_id INT PRIMARY KEY AUTO_INCREMENT,
    TrainID INT NOT NULL,                                  -- Foreign key referencing the Trains table
    coach_type ENUM('Sleeper', 'AC 3-Tier', 'AC 2-Tier', 'AC 1-Tier', 'General') NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    FOREIGN KEY (TrainID) REFERENCES Trains(TrainID)      -- Foreign key reference to train_id in Trains table
);
Select *
from Coaches;
CREATE TABLE TicketFareDiscounts (
    fare_discount_id INT PRIMARY KEY AUTO_INCREMENT,
    coach_type ENUM('Sleeper', 'AC 3-Tier', 'AC 2-Tier', 'AC 1-Tier', 'General') NOT NULL,
    base_fare DECIMAL(10, 2) NOT NULL,
    discount_code VARCHAR(50) UNIQUE,                     
    discount_percentage DECIMAL(5, 2),                   
    valid_from DATE,                                      
    valid_until DATE,                                    
    applicable_to ENUM('All', 'SeniorCitizen', 'Student', 'Festival', 'VIP'), -- Who can use the discount
    description TEXT,                                   
    UserID INT,                                          
    TrainID INT,                      
    coach_id INT,
    FOREIGN KEY (UserID) REFERENCES Users1(UserID),      
    FOREIGN KEY (TrainID) REFERENCES Trains(TrainID),
    FOREIGN KEY (coach_id) REFERENCES Coaches(coach_id)
);
select *
from TicketFareDiscounts;
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,          
   UserID INT NOT NULL,                                
    TicketID INT NOT NULL,                            
    TrainID INT NOT NULL,                              
    amount DECIMAL(10, 2) NOT NULL,                    
    payment_method ENUM('Credit Card', 'Debit Card', 'Net Banking', 'UPI', 'Wallet') NOT NULL, 
    transaction_id VARCHAR(50) NOT NULL UNIQUE,         
    status ENUM('Pending', 'Completed', 'Failed', 'Cancelled') NOT NULL DEFAULT 'Pending',  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    FOREIGN KEY (UserID) REFERENCES Users1(UserID),    
    FOREIGN KEY (TicketID) REFERENCES Tickets(TicketID), 
    FOREIGN KEY (TrainID) REFERENCES Trains(TrainID)
);
Select *
from Payments;
INSERT INTO Trains (TrainName, Source, Destination, TotalSeats, AvailableSeats) 
VALUES ('Shatabdi Express', 'Delhi', 'Mumbai', 500, 450);
UPDATE Trains 
SET AvailableSeats = 400 
WHERE TrainID = 1;

SELECT * 
FROM Tickets 
WHERE UserID = (SELECT UserID FROM Users1 WHERE UserID = 2);

SELECT * 
FROM Tickets 
WHERE TrainID = (SELECT TrainID FROM Trains WHERE TrainID = 2) 
AND Status = 'Booked';

SELECT coach_type, available_seats 
FROM Coaches AS c1 
WHERE available_seats < (SELECT available_seats FROM Coaches AS c2 WHERE coach_id = 1);

SELECT COUNT(*) AS total_coaches 
FROM Coaches AS c1 
WHERE TrainID = (SELECT TrainID FROM Coaches AS c2 WHERE coach_id = 2);

    
SELECT 
    rc.customer_id,
    rc.name,
    rc.email,
    p.payment_id,
    p.amount,
    p.payment_method,
    p.status AS PaymentStatus
FROM 
    RailwayCustomers rc
JOIN 
    Payments p ON rc.customer_id = p.UserID; -- Assuming UserID refers to customer_id

SELECT 
    t.TicketID,
    t.SeatNumber,
    t.BookingDate,
    t.Status AS TicketStatus,
    tr.TrainName,
    tr.Source,
    tr.Destination
FROM 
    Tickets t
JOIN 
    Trains tr ON t.TrainID = tr.TrainID;

SELECT 
    t.TicketID,
    t.SeatNumber,
    t.BookingDate,
    t.Status AS TicketStatus,
    u.Username
FROM 
    Tickets t
JOIN 
    Users1 u ON t.UserID = u.UserID;

