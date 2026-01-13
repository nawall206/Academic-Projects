/* MySQL file for Queries  */

-- Q1) List all films released after 2021 
SELECT Title, ReleaseYear
FROM FILM
WHERE ReleaseYear > 2021;

-- Q2) List nominees whose name starts and ends with same letter
SELECT PersonName
FROM NOMINEE
WHERE LOWER(LEFT(PersonName,1)) = LOWER(RIGHT(PersonName,1));

-- Q3) List all ceremonies that took place in March.
SELECT Year, CeremonyDate, CeremonyName FROM CEREMONY WHERE MONTH(CeremonyDate) = 3;

-- Q4) Find the longest film title in the database
SELECT Title FROM FILM ORDER BY LENGTH(Title) DESC LIMIT 1;

--Q5) Find the weekday on which each ceremony took place.
SELECT Year, CeremonyDate, DAYNAME(CeremonyDate) AS Weekday FROM CEREMONY;

--Q6) Show each category and the number of films nominated in it.
SELECT categoryName, COUNT(*) AS TotalNominations FROM NOMINATION GROUP BY categoryName ORDER BY TotalNominations DESC

-- Q7) Find the film with the highest number of nominations.
SELECT Title, COUNT(*) AS NominationsCount FROM NOMINATION GROUP BY Title ORDER BY NominationsCount DESC LIMIT 1;

-- Q8) Show how many awards were won for each year 
SELECT Year, SUM(IsWinner) AS TotalAwardsWon FROM NOMINATION GROUP BY Year ORDER BY Year;

-- Q9) Find nominees who have won more than one Oscar award
SELECT      n.PersonName,     COUNT(*) AS TotalAwardsWon FROM NOMINEE n JOIN NOMINATION nom     ON n.Year
= nom.Year    AND n.categoryName = nom.categoryName    AND n.Title = nom.Title WHERE nom.IsWinner = 1 GROUP BY n.PersonName HAVING COUNT(*) > 1 ORDER BY TotalAwardsWon DESC;

-- Q10) Show the top 5 categories with the highest number of Oscar wins.
ELECT categoryName, SUM(IsWinner) AS TotalWins FROM NOMINATION GROUP BY categoryName HAVING SUM(IsWinner) > 0 ORDER BY TotalWins DESC LIMIT 5;
