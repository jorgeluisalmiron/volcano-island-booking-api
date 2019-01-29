
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

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (1, current_date+1,current_date+3,2,1,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (1,current_date+1,1);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (2,current_date+2,1);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (2, current_date+3,current_date+6,5,2,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (3,current_date+3,2);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (4,current_date+4,2);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (5,current_date+5,2);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (3, current_date+6,current_date+8,7,1,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (6,current_date+6,3);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (7,current_date+7,3);

INSERT INTO `bookings`(`id`,`arrival_on`,`departure_on`,`qty_persons`,`client_id`,`status`) VALUES (4, current_date+10,current_date+12,10,2,'booked');
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (8,current_date+10,4);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (9,current_date+11,4);
INSERT INTO `dates_by_booking` (`id`, `date`, `booking_id`) VALUES (10,current_date+12,4);
