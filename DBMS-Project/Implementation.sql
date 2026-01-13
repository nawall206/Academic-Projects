/* MySQL file for creating tables  */

-- create Location table
CREATE TABLE LOCATION (
    VenueName VARCHAR(100) PRIMARY KEY,
    City VARCHAR(60) NOT NULL,
    Country VARCHAR(60) NOT NULL
);

-- create Ceremony table
CREATE TABLE CEREMONY (
    Year INT PRIMARY KEY,
    CeremonyNumber INT NOT NULL,SH
    CeremonyName VARCHAR(100) NOT NULL,
    CeremonyDate DATE NOT NULL,
    VenueName VARCHAR(100) NOT NULL,
    FOREIGN KEY (VenueName) REFERENCES LOCATION(VenueName)
);


-- create Category table
CREATE TABLE CATEGORY (
    categoryName VARCHAR(120) PRIMARY KEY,
    canonicalName VARCHAR(120) NOT NULL
);

-- create Film table
CREATE TABLE FILM (
    Title VARCHAR(150) PRIMARY KEY,
    ReleaseYear INT NOT NULL
);

-- Create Nomination Table
CREATE TABLE NOMINATION (
    Year INT NOT NULL,
    categoryName VARCHAR(120) NOT NULL,
    Title VARCHAR(150) NOT NULL,
    IsWinner BOOLEAN NOT NULL,
    FilmAge INT,     -- derived (may leave NULL, not physically stored)
    PRIMARY KEY (Year, categoryName, Title),
    FOREIGN KEY (Year) REFERENCES CEREMONY(Year),
    FOREIGN KEY (categoryName) REFERENCES CATEGORY(categoryName),
    FOREIGN KEY (Title) REFERENCES FILM(Title)
);

-- Create Nominee Table
CREATE TABLE NOMINEE (
    PersonName VARCHAR(150) NOT NULL,
    role VARCHAR(60),
    Year INT NOT NULL,
    categoryName VARCHAR(120) NOT NULL,
    Title VARCHAR(150) NOT NULL,
    PRIMARY KEY (PersonName, Year, categoryName, Title),
    FOREIGN KEY (Year, categoryName, Title)
        REFERENCES NOMINATION(Year, categoryName, Title)
);

-- show tables created in database
SHOW TABLES;

-- describe each table
DESC CEREMONY;
DESC CATEGORY;
DESC FILM;
DESC NOMINATION;
DESC NOMINEE;
DESC LOCATION;
