DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation (
  reservation_id int NOT NULL AUTO_INCREMENT,
  room_number int NOT NULL,
  seats_number int NOT NULL,
  movie_title varchar(100) NOT NULL,
  PRIMARY KEY (reservation_id)
);

INSERT INTO reservation (room_number, seats_number, movie_title)
 VALUES (1, 2, 'Kiler');

INSERT INTO reservation (room_number, seats_number, movie_title)
 VALUES (1, 3, 'Kiler');

INSERT INTO reservation (room_number, seats_number, movie_title)
 VALUES (2, 2, 'Anabel');

CREATE TABLE client (
  client_id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  surname varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  is_paid BIT NOT NULL,
  reservation_id int NOT NULL,
  PRIMARY KEY (client_id)
);

INSERT INTO client (name, surname, email, is_paid, reservation_id)
 VALUES ('Jan', 'Kowalski', 'piotr.nowak@gmail.com', 1, 1);

INSERT INTO client (name, surname, email, is_paid, reservation_id)
 VALUES ('Izabela', 'Kowalska', 'izabela.kowalska@gmail.com', 1, 2);

INSERT INTO client (name, surname, email, is_paid, reservation_id)
 VALUES ('Piotr', 'Nowak', 'piotr.nowak@gmail.com', 0, 3);