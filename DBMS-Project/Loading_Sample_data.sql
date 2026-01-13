/* MySQL file for Loading Data  */

-- Create a temporary table matching CSV 
CREATE TABLE OSCAR_RAW (
    year_film      INT,
    year_ceremony  INT,
    ceremony       INT,
    category       VARCHAR(120),
    canon_category VARCHAR(120),
    name           VARCHAR(150),
    film           VARCHAR(150),
    winner         VARCHAR(5)
);

--Load CSV data into the temporary created table 
LOAD DATA LOCAL INFILE '/home/nawal_nadim/Desktop/Oscars_Awards.csv'
INTO TABLE OSCAR_RAW
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

-- Fill data from Oscar_Raw
-- Location table is filled with data manually by searching their location on wikipedia 
INSERT INTO LOCATION (VenueName, City, Country)
VALUES ('Dolby Theatre', 'Los Angeles', 'USA'), ('Union Station', 'Los Angeles', 'USA');


-- Next ceremony data is filled in ceremony table 
-- The CEREMONY table includes attributes that are NOT NULL, but these values are not provided in the CSV dataset. Therefore, CeremonyDate is temporarily changed to allow NULL so that data can be inserted from OSCAR_RAW. After insertion, the missing values (ceremony dates) are added manually using verified public sources (e.g., official Academy Awards data). Finally, the column is restored to NOT NULL to maintain integrity constraints.

INSERT INTO CEREMONY (Year, CeremonyNumber, CeremonyName, CeremonyDate, VenueName)
SELECT DISTINCT
year_ceremony,
ceremony,
CONCAT('Academy Awards #', ceremony),
NULL,
CASE
WHEN year_ceremony = 2021 THEN 'Union Station'
ELSE 'Dolby Theatre'
END
FROM OSCAR_RAW
WHERE year_ceremony BETWEEN 2020 AND 2024;

-- Temporarily allow CeremonyDate to be NULL
ALTER TABLE CEREMONY
MODIFY COLUMN CeremonyDate DATE NULL;

-- Insert ceremony data from the CSV (OSCAR_RAW)
INSERT INTO CEREMONY (Year, CeremonyNumber, CeremonyName, CeremonyDate, VenueName)
SELECT DISTINCT year_ceremony, ceremony, CONCAT('Academy Awards #', ceremony),NULL, CASE WHEN year_ceremony = 2021 THEN 'Union Station' ELSE 'Dolby Theatre' END FROM OSCAR_RAW
WHERE year_ceremony BETWEEN 2021 AND 2024;

-- Manually add real ceremony dates (from Wikipedia)
UPDATE CEREMONY SET CeremonyDate = '2021-04-25' WHERE Year = 2021;
UPDATE CEREMONY SET CeremonyDate = '2022-03-27' WHERE Year = 2022;
UPDATE CEREMONY SET CeremonyDate = '2023-03-12' WHERE Year = 2023;
UPDATE CEREMONY SET CeremonyDate = '2024-03-10' WHERE Year = 2024;

-- Make CeremonyDate NOT NULL again
ALTER TABLE CEREMONY
MODIFY COLUMN CeremonyDate DATE NOT NULL;

-- Insert data for category values from the OSCAR_RAW dataset.
-- Both the displayed category name and its canonical form are captured.
INSERT INTO CATEGORY (CategoryName, canonicalName)
SELECT DISTINCT
    category,
    canon_category
FROM OSCAR_RAW
WHERE year_ceremony BETWEEN 2021 AND 2024;

-- Insert data for films from OSCAR_RAW.
-- Rows with empty film titles are excluded to avoid primary key violations.
INSERT INTO FILM (Title, ReleaseYear)
SELECT DISTINCT
    film,
    year_film
FROM OSCAR_RAW
WHERE year_ceremony BETWEEN 2021 AND 2024
  AND film IS NOT NULL
  AND film <> '';
  
-- Insert unique nomination records from OSCAR_RAW.
-- DISTINCT ensures each film-category-year nomination is stored only once.
-- isWinner is mapped to 1/0.
INSERT INTO NOMINATION (Year, categoryName, Title, IsWinner)
SELECT year_ceremony, category, film,MAX( CASE WHEN LOWER(TRIM(winner)) = 'true' THEN 1 WHEN LOWER(TRIM(winner)) LIKE '%rue%' THEN 1 WHEN LOWER(TRIM(winner)) LIKE '%winne%' THEN 1 ELSE 0 END AS CleanWinner FROM OSCAR_RAW WHERE year_ceremony BETWEEN 2021 AND 2024 AND category IS NOT NULL AND category <> '' AND film IS NOT NULL AND film <> ''GROUP BY year_ceremony, category, film;

-- Insert nominee records as a weak entity referencing the NOMINATION table.
-- Each nominee is linked to the composite PK (Year, categoryName, Title).
INSERT INTO NOMINEE (PersonName, role, Year, categoryName, Title)
SELECT
    name,
    NULL,                  
    year_ceremony,
    category,
    film
FROM OSCAR_RAW
WHERE year_ceremony BETWEEN 2021 AND 2024
  AND name IS NOT NULL AND name <> ''
  AND category IS NOT NULL AND category <> ''
  AND film IS NOT NULL AND film <> '';

-- Compute the derived attribute FilmAge for each nomination.
UPDATE NOMINATION n
JOIN FILM f ON n.Title = f.Title
SET n.FilmAge = n.Year - f.ReleaseYear
WHERE f.ReleaseYear > 0;

