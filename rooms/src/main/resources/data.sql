DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS room;

CREATE TABLE room (
  room_id int NOT NULL AUTO_INCREMENT,
  room_number int NOT NULL,
  seats_number int NOT NULL,
  PRIMARY KEY (room_id)
);

INSERT INTO room (room_number, seats_number)
 VALUES (1, 5);

INSERT INTO room (room_number, seats_number)
 VALUES (2, 10);

CREATE TABLE seat (
  seat_id int NOT NULL AUTO_INCREMENT,
  seat_number int NOT NULL,
  is_bocked BIT NOT NULL,
  room_number int NOT NULL,
  room_id int NOT NULL,
  PRIMARY KEY (seat_id)
);

INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (1, 0, 1, 1);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (2, 1, 1, 1);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (3, 1, 1, 1);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (4, 0, 1, 1);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (5, 0, 1, 1);

INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (1, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (2, 1, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (3, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (4, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (5, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (6, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (7, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (8, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (9, 0, 2, 2);
INSERT INTO seat (seat_number, is_bocked, room_number, room_id)
 VALUES (10, 0, 2, 2);