/* MySQL file for Advanced Features  */

-- Stored Procedure 1: Adding Nomination safely 
DELIMITER $$

CREATE PROCEDURE AddNomination(
    IN pYear INT,
    IN pCategory VARCHAR(120),
    IN pTitle VARCHAR(150),
    IN pIsWinner TINYINT
)
BEGIN DECLARE existsCount INT DEFAULT 0;
SELECT COUNT(*) INTO existsCount
FROM NOMINATION WHERE Year = pYear AND categoryName = pCategory AND Title = pTitle;  IF existsCount = 0 THEN
INSERT INTO NOMINATION (Year, categoryName, Title, IsWinner)
VALUES (pYear, pCategory, pTitle, pIsWinner);
END IF;
END $$

DELIMITER ;

-- calling procedure 
CALL AddNomination(2023, 'DIRECTING', 'Barbie', 0);

-- verifying if nomination is added 
SELECT * FROM NOMINATION WHERE Year = 2023 AND categoryName = 'DIRECTING' AND Title = 'Barbie';



-- Stored Procedure 2: All winners for given year
DELIMITER $$

CREATE PROCEDURE GetWinnersByYear( IN pYear INT)
BEGIN SELECT n.PersonName, nom.Title, nom.categoryName AS Category, nom.Year FROM NOMINEE JOIN NOMINATION nom
ON n.Year = nom.Year AND n.categoryName = nom.categoryName
AND n.Title = nom.Title WHERE nom.Year = pYear
AND nom.IsWinner = 1 ORDER BY nom.categoryName, n.PersonName;
END $$


DELIMITER ;

-- calling procedure
CALL GetWinnersByYear(2023);

-- Trigger 1: Calculating FilmAge before inserting Nomination 
DELIMITER $$
CREATE TRIGGER before_nomination_insert
BEFORE INSERT ON NOMINATION
FOR EACH ROW
BEGIN
    DECLARE filmYear INT;
    SELECT ReleaseYear 
    INTO filmYear 
    FROM FILM 
    WHERE Title = NEW.Title;
    SET NEW.FilmAge = NEW.Year - filmYear;
END$$
DELIMITER ;

-- Testing Trigger
INSERT INTO NOMINATION (Year, categoryName, Title, IsWinner) VALUES (2024, 'BEST PICTURE', 'Dune', 0);

-- Verifying Trigger 
SELECT Year, categoryName, Title, FilmAge FROM NOMINATION WHERE Title='Dune' AND Year=2024 AN
D categoryName='BEST PICTURE';

-- View : All Winning Nomination 
CREATE VIEW View_Winners AS SELECT Year, categoryName, Title,
IsWinner FROM NOMINATION WHERE IsWinner = 1;

SELECT * FROM View_Winners LIMIT 10;

-- Index 
CREATE INDEX idx_categoryName ON NOMINATION (categoryName);
SHOW INDEX FROM NOMINATION;
