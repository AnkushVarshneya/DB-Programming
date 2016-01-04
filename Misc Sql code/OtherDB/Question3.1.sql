--Phone data application
--This file is for creating and populating data into tables


--The is delete table if there are table with the same name in the current database
--Will display error if table with the same name does not exist, can be ignored

begin transaction;

drop table if exists phone;
drop table if exists model;
drop table if exists SoC;
drop table if exists software;
drop table if exists camera;
drop table if exists display;

--creating the Table called phone
create table phone(
	imie integer NOT NULL primary key,
	status varchar(10),
	modelID varchar(5),
	foreign key (modelID) references model(modelID) on delete cascade
	);

--inserting data into the table facilities
insert into phone values (1,'stolen', 'C6603');

insert into phone values (2, 'lost', 'C6906');

insert into phone values (3, 'active', 'D6502');

insert into phone values (4, 'stolen', 'RM-819');

insert into phone values (5, 'lost', 'RM-821');

insert into phone values (6, 'active', 'RM-876');

insert into phone values (7, 'stolen', 'A1533');

insert into phone values (8, 'lost', 'A1428');

insert into phone values (9, 'active', 'z10');

insert into phone values (10, 'stolen', 'z30');

insert into phone values (11, 'lost', 'C6603');

insert into phone values (12, 'active', 'C6906');

insert into phone values (13, 'stolen', 'D6502');

insert into phone values (14, 'lost', 'RM-819');

insert into phone values (15, 'active', 'RM-821');

insert into phone values (16, 'stolen', 'RM-876');

insert into phone values (17, 'lost', 'A1533');

insert into phone values (18, 'active', 'A1428');

insert into phone values (19, 'stolen', 'z10');

insert into phone values (20, 'lost', 'z30');

insert into phone values (21, 'active', 'C6603');

insert into phone values (22, 'stolen', 'C6906');

insert into phone values (23, 'lost', 'D6502');

insert into phone values (24, 'active', 'RM-819');

insert into phone values (25, 'stolen', 'RM-821');

insert into phone values (26, 'lost', 'RM-876');

insert into phone values (27, 'active', 'A1533');

insert into phone values (28, 'stolen', 'A1428');

insert into phone values (29, 'lost', 'z10');

insert into phone values (30, 'stolen', 'z30');

--creating the Table called model
create table model (
	modelID varchar(5) NOT NULL primary key,
	model_name varchar(50),
	dimention varchar(50),
	weight varchar(20),
	connectivity varchar(50),
	ports varchar(50),
	sensor varchar(50),
	battery  varchar(50),
	storage float,
	ram float,
	manufacture varchar(50),
	chipset varchar(15),
	os varchar(20),
	version varchar(5),
	foreign key (chipset)  references SoC(chipset) on delete cascade,
	foreign key (os)  references software(os) on delete cascade,
	foreign key (version)  references software(version) on delete cascade
	);

--inserting data into table model
insert into model values
	('C6603',
	'Sony Xperia Z',
	'139 x 71 x 7.9 mm (5.47 x 2.80 x 0.31 in)',
	'146 g (5.15 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 2330 mAh battery',
	16,
	2,
	'Sony',
	'Snapdragon S4',
	'Android',
	'4.2'
	);

insert into model values
	('C6906',
	'Sony Xperia Z1',
	'144 x 74 x 8.5 mm (5.67 x 2.91 x 0.33 in)',
	'170 g (6.00 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 3000 mAh battery',
	16,
	2,
	'Sony',
	'Snapdragon 800',
	'Android',
	'4.3'
	);

insert into model values
	('D6502',
	'Sony Xperia Z2',
	'146.8 x 73.3 x 8.2 mm (5.78 x 2.89 x 0.32 in)',
	'163 g (5.75 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 3200 mAh battery',
	16,
	3,
	'Sony',
	'Snapdragon 801',
	'Android',
	'4.4'
	);

insert into model values
	('RM-819',
	'Nokia Lumia 800',
	'116.5 x 61.2 x 12.1 mm, 76.1 cc (4.59 x 2.41 x 0.48 in)',
	'142 g (5.01 oz)',
	'3G, 2G, WIFI, BLUETOOTH',
	'USB, Sound',
	'Accelerometer, proximity, compass',
	'Non-removable Li-Ion 1450 mAh battery',
	16,
	0.5,
	'Nokia',
	'Snapdragon S2',
	'Windows',
	'7'
	);

insert into model values
	('RM-821',
	'Nokia Lumia 920',
	'130.3 x 70.8 x 10.7 mm, 99 cc (5.13 x 2.79 x 0.42 in)',
	'185 g (6.53 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 2000 mAh battery',
	32,
	1,
	'Nokia',
	'Snapdragon S3',
	'Windows',
	'8'
	);

insert into model values
	('RM-876',
	'Nokia Lumia 1020',
	'130.4 x 71.4 x 10.4 mm, 96.9 cc (5.13 x 2.81 x 0.41 in)',
	'158 g (5.57 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 3200 mAh battery',
	32,
	2,
	'Nokia',
	'Snapdragon S3',
	'Windows',
	'8'
	);

insert into model values
	('A1533',
	'iPhone 5s',
	'123.8 x 58.6 x 7.6 mm (4.87 x 2.31 x 0.30 in)',
	'112 g (3.95 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Po 1560 mAh battery',
	64,
	1,
	'Apple',
	'Apple A7',
	'IOS',
	'7'
	);

insert into model values
	('A1428',
	'iPhone 5',
	'123.8 x 58.6 x 7.6 mm (4.87 x 2.31 x 0.30 in)',
	'112 g (3.95 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Po 1440 mAh battery',
	64,
	1,
	'Apple',
	'Apple A6',
	'IOS',
	'6'
	);
	
insert into model values
	('z10',
	'BlackBerry Z10',
	'130 x 65.6 x 9 mm (5.12 x 2.58 x 0.35 in)',
	'137.5 g (4.83 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Ion 1800 mAh battery',
	16,
	2,
	'Blackberry',
	'Snapdragon S3',
	'Blackbery OS',
	'10.x'
	);

insert into model values
	('z30',
	'BlackBerry Z30',
	'140.7 x 72 x 9.4 mm (5.54 x 2.83 x 0.37 in)',
	'170 g (6.00 oz)',
	'LTE, 3G, 2G, WIFI, BLUETOOTH, NCF',
	'USB, Sound',
	'Accelerometer, gyro, proximity, compass',
	'Non-removable Li-Po 2880 mAh battery',
	16,
	2,
	'Blackberry',
	'Snapdragon 800',
	'Blackbery OS',
	'10.x'
	);

--creating the table called SoC
create table SoC(
	chipset varchar(15) NOT NULL primary key,
	gpu varchar(20),
	cpu_core int,
	cpu_speed int
	);

--inserting data into table SoC
insert into SoC values ('Snapdragon 800', 4, 2.2, 'Adreno 330');

insert into SoC values ('Snapdragon 801', 4, 2.5, 'Adreno 330');

insert into SoC values ('Snapdragon S2', 1, 1.4 , 'Adreno 205');

insert into SoC values ('Snapdragon S3', 2, 1.5, 'Adreno 225');

insert into SoC values ('Snapdragon S4', 4, 1.5, 'Adreno 320');

insert into SoC values ('Apple A7', 2, 1.3, 'PowerVR G6430');

insert into SoC values ('Apple A6', 2, 1.3, 'PowerVR SGX 543MP3');

	
--creating the table called software
create table software(
	os varchar(20) NOT NULL,
	version varchar(5) NOT NULL,
	primary key(os, version)
	);

--inserting data for table software
insert into software values	('Android', '4.2');

insert into software values	('Android',	'4.3');

insert into software values	('Android',	'4.4');

insert into software values	('Windows',	'7');

insert into software values	('Windows',	'8');

insert into software values	('IOS', '7');

insert into software values	('IOS', '6');
	
insert into software values	('Blackbery OS', '10.x');

--creating the table called camera
create table camera(
	modelID varchar(5) NOT NULL,
	megapixel integer,
	resolution varchar(20),
	zoom varchar(20),
	video varchar(50),
	features varchar(100),
	camera_type varchar(5),
	primary key(modelID, megapixel)
	);

--inserting data for table camera
insert into camera values
	('C6603',
	13,
	'4128 x 3096 pixels',
	'digital',
	'1080p@30fps',
	'Geo-tagging, touch focus, face detection, image stabilization, HDR, sweep panorama',
	'Primary'
	);

insert into camera values
	('C6603',
	2,
	'1416 x 1416 pixels',
	'none',
	'1080p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('C6906',
	20.7,
	'5248 х 3936 pixels',
	'digital',
	'1080p@30fps',
	'1/2.3'' sensor size, geo-tagging, touch focus, face detection, image stabilization, HDR, panorama',
	'Primary'
	);

insert into camera values
	('C6906',
	2,
	'1416 x 1416 pixels',
	'none',
	'1080p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('D6502',
	20.7,
	'5248 х 3936 pixels',
	'digital',
	'1080p@30fps',
	'1/2.3'' sensor size, geo-tagging, touch focus, face detection, image stabilization, HDR, panorama',
	'Primary'
	);

insert into camera values
	('D6502',
	2,
	'1416 x 1416 pixels',
	'none',
	'1080p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('RM-819',
	8,
	'3264 x 2448 pixels',
	'digital',
	'730p@30fps',
	'Geo-tagging, Carl Zeiss optics, autofocus, dual-LED flash',
	'Primary'
	);

insert into camera values
	('RM-821',
	8,
	'3264 x 2448 pixels',
	'digital',
	'1080p@30fps',
	'PureView technology, geo-tagging, touch focus, Carl Zeiss optics, autofocus, dual-LED flash',
	'Primary'
	);

insert into camera values
	('RM-821',
	1.3,
	'1152 x 864 pixels',
	'none',
	'720p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('RM-876',
	41,
	'7152 x 5368 pixels',
	'4x',
	'1080p@30fps',
	'1/1.5'' sensor size, 1.12 µm pixel size, PureView technology, geo-tagging, face detection, dual capture, panorama',
	'Primary'
	);

insert into camera values
	('RM-876',
	1.3,
	'1152 x 864 pixels',
	'none',
	'720p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('A1533',
	8,
	'3264 x 2448 pixels',
	'digital',
	'1080p@30fps',
	'1/3'' sensor size, 1.5 µm pixel size, simultaneous HD video and image recording, touch focus, geo-tagging, face detection, HDR panorama, HDR photo',
	'Primary'
	);

insert into camera values
	('A1533',
	1.2,
	'1152 x 864 pixels',
	'none',
	'720p@30fps',
	'face detection, FaceTime over Wi-Fi or Cellular',
	'Secondary'
	);

insert into camera values
	('A1428',
	8,
	'3264 x 2448 pixels',
	'digital',
	'1080p@30fps',
	'1/3.2'' sensor size, 1.4 µm pixel size, simultaneous HD video and image recording, touch focus, geo-tagging, face detection, panorama, HDR photo',
	'Primary'
	);

insert into camera values
	('A1428',
	1.2,
	'1152 x 864 pixels',
	'none',
	'720p@30fps',
	'face detection, FaceTime over Wi-Fi or Cellular',
	'Secondary'
	);

insert into camera values
	('z10',
	8,
	'3264 x 2448 pixels',
	'digital',
	'1080p@30fps',
	'Geo-tagging, face detection, image stabilization, HDR (via software update)',
	'Primary'
	);

insert into camera values
	('z10',
	2,
	'1416 x 1416 pixels',
	'none',
	'720p@30fps',
	'none',
	'Secondary'
	);

insert into camera values
	('z30',
	8,
	'3264 x 2448 pixels',
	'digital',
	'1080p@30fps',
	'Geo-tagging, face detection, image stabilization, HDR',
	'Primary'
	);

insert into camera values
	('z30',
	2,
	'1416 x 1416 pixels',
	'none',
	'720p@30fps',
	'none',
	'Secondary'
	);


--creating a table called display
create table display(
	modelID varchar(5) NOT NULL,
	screensize float NOT NULL,
	resolution varchar(20),
	touchtype varchar(20),
	pixel_density integer,
	material_used varchar(20),
	primary key(modelID, screensize)
	);

--inserting data for table service_suscribers
insert into display values
	('C6603',
	5.0,	
	'1080 x 1920 pixels', 
	'Multitouch up to 10 fingers',
	441,
	'Shatter proof and scratch-resistant glass'
	);

insert into display values
	('C6906',
	5.0,	
	'1080 x 1920 pixels', 
	'Multitouch up to 10 fingers',
	441,
	'Shatter proof and scratch-resistant glass'
	);

insert into display values
	('D6502',
	5.2,
	'1080 x 1920 pixels', 
	'Multitouch up to 10 fingers',
	424,
	'Shatter proof and scratch-resistant glass'
	);

insert into display values
	('RM-819',
	3.7,
	'480 x 800 pixels', 
	'Multitouch',
	252 ,
	'Corning Gorilla Glass - Nokia ClearBlack display'
	);

insert into display values
	('RM-821',
	4.5,
	'768 x 1280 pixels', 
	'Multitouch',
	332 ,
	'Corning Gorilla Glass 2 - PureMotion HD+ ClearBlack display'
	);

insert into display values
	('RM-876',
	4.5,
	'768 x 1280 pixels', 
	'Multitouch',
	332 ,
	'Corning Gorilla Glass 3 - PureMotion HD+ ClearBlack display'
	);

insert into display values
	('A1533',
	4.0,
	'640 x 1136 pixels', 
	'Multitouch',
	326 ,
	'Corning Gorilla Glass, oleophobic coating'
	);

insert into display values
	('A1428',
	4.0,
	'640 x 1136 pixels', 
	'Multitouch',
	326 ,
	'Corning Gorilla Glass, oleophobic coating'
	);
	
insert into display values
	('z10',
	4.2,
	'768 x 1280 pixels', 
	'Multitouch',
	355,
	'Glass'
	);

insert into display values
	('z30',
	5,
	'768 x 1280 pixels', 
	'Multitouch',
	294,
	'Glass'
	);

commit;