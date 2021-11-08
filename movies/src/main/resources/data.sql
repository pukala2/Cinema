DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
  movie_id int NOT NULL AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  category varchar(100) NOT NULL,
  PRIMARY KEY (movie_id)
);

INSERT INTO movie (title, category)
 VALUES ('Kiler', 'Comedy');

INSERT INTO movie (title, category)
 VALUES ('Anabel', 'Horror');

INSERT INTO movie (title, category)
 VALUES ('James Bond', 'Action');