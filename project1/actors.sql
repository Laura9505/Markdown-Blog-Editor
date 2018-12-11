DROP TABLE IF EXISTS Actors;

-- create table
CREATE TABLE Actors (
    name VARCHAR(40), 
    movie VARCHAR(80), 
    year INTEGER, 
    role VARCHAR(40)
);

-- load data
LOAD DATA LOCAL INFILE './actors.csv' INTO TABLE Actors FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

-- query: give the names of all the actors in the movie 'Die Another Day'
SELECT name
FROM Actors
WHERE movie = 'Die Another Day';

-- drop table
DROP TABLE Actors;