
--rules
INSERT INTO `rules`(`name`,`min`,`max`) values ('STAY_RULE',1,3);
INSERT INTO `rules`(`name`,`min`,`max`) values ('RESERVED_DAYS_RULE',1,30);
INSERT INTO `rules`(`name`,`min`,`max`) values ('PERSONS_PER_BOOKING_RULE',1,10);

--sample data
INSERT INTO `customers`
(`id`,`email`,`first_name`,`ident_number`,`ident_type`,`last_name`)
VALUES (1,'george_from_cuenca@testmail.com','Jorge','DNI','1111111XX','Almiron');
INSERT INTO `customers`
(`id`,`email`,`first_name`,`ident_number`,`ident_type`,`last_name`)
VALUES (2,'luislorenzoni@testmail.com','Luis','DNI','1111111XX','Lorenzoni');
INSERT INTO `customers`
(`id`,`email`,`first_name`,`ident_number`,`ident_type`,`last_name`)
VALUES (3,'luciobb@testmail.com','Lucio','DNI','1111111XX','Gastal');

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (1, '2019-01-30','2019-02-01',2,1,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (1,'2019-01-30',1);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (2,'2019-01-31',1);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (2, '2019-02-01','2019-02-04',5,2,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (3,'2019-02-01',2);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (4,'2019-02-02',2);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (5,'2019-02-03',2);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (3, '2019-01-30','2019-02-01',7,1,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (6,'2019-02-06',3);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (7,'2019-02-07',3);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (4, '2019-02-01','2019-02-04',10,2,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (8,'2019-02-08',4);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (9,'2019-02-09',4);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (10,'2019-02-10',4);
