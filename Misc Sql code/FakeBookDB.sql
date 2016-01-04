--SQL Fake Book Song Data
--Ankush Varshneya 100853074

--CREATE DATABASE TABLES
--=======================

create table if not exists songs(
      id integer primary key not null, --auto increment key 
      songTitle text NOT NULL, --title of the songs
      bookCode text NOT NULL,  --book code for the the fake book the song is from
      page int, --page number in book where song appears
      student_number text NOT NULL  --student who contributed the data
      );

--INSERT DATA
--=======================

begin transaction;

--Insert songs

insert into songs(songTitle, bookCode, page, student_number) values ('BASIN STREET BLUES','SPC6', 218, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('BEAU KOO JACK','SPC6', 219, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('STRUTTIN'' WITH SOME BARBEQUE','SPC6', 220, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('TERRIBLE BLUES/S.O.L.BLUES/MAHAGONY HALL','SPC6', 221, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('WEST END BLUES','SPC6', 223, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('BLUES','SPC6', 225, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('MINORITY','SPC6', 227, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('ON IT','SPC6', 229, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('PRETTY FOR THE PEOPLE','SPC6', 231, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('RECORDA-ME','SPC6', 233, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('WOODY N''YOU','SPC6', 235, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('BLUE TRAIN','SPC6', 237, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('I''M OLD FASHIONED','SPC6', 239, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('SIDEWINDER','SPC6', 240, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('I COVER THE WATERFRONT','SPC6', 242, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('LOUISIANA SWING','SPC6', 244, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('I CAN''T GET STARTED','SPC6', 246, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('ONE BASS HIT','SPC6', 248, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('OH! KAREN O','SPC6', 251, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('STRAIGHT, NO CHASER','SPC6', 254, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('BIRD''S MOTHER','SPC6', 140, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('W.K. BLUES','SPC6', 257, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('FEEDIN'' THE BEAN','SPC6', 258, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('TETE A TETE','SPC6', 261, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('GOOD MORNING STARSHINE','SPC6', 263, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('BODY & SOUL','SPC6', 264, '100853074');
 
insert into songs(songTitle, bookCode, page, student_number) values ('KERA''S DANCE','SPC6', 266, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('GINGERBREAD BOY','SPC6', 269, '100853074');
 
insert into songs(songTitle, bookCode, page, student_number) values ('SOFTLY IN A MORNING SUNRISE','SPC6', 271, '100853074');

insert into songs(songTitle, bookCode, page, student_number) values ('LUCRETIA''S REPRISE','SPC6', 273, '100853074');

end transaction;