package com.challenge.volcano.island.services;


import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.exceptions.NoAvailabilityException;
import com.challenge.volcano.island.model.Booking;
import com.challenge.volcano.island.model.Customer;
import com.challenge.volcano.island.repositories.BookingRepository;
import com.challenge.volcano.island.repositories.DatesByBookingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookingServicesTest {


    @Mock
    private DatesByBookingRepository datesByBookingRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AvailabilitiesServiceImpl availabilitiesService;

    @InjectMocks
    private BookingServiceImpl bookingService = new BookingServiceImpl();


    private static final int MAX_PERSONS = 1000;
    private static final int MAX_DAYS = 30;


    @Before
    public void initMocks() {
        availabilitiesService = Mockito.spy(new AvailabilitiesServiceImpl());
        availabilitiesService.setAvailabilitiesMaxDays(MAX_DAYS);
        availabilitiesService.setMaxPersons(MAX_PERSONS);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void bookingTest() {
        try {
            when(datesByBookingRepository.findAllByRangeOfDates(any(), any())).thenReturn(null);
            LocalDate date = LocalDate.now();
            LocalDate arrivalOn = date.plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = date.plus(4, ChronoUnit.DAYS);
            int qtyPersons = 8;
            when(bookingRepository.save(any())).thenReturn(getBooking(arrivalOn, departureOn, qtyPersons));
            bookingService.performBooking(getCustomer(), arrivalOn,
                    departureOn, qtyPersons);
            int days = (int) DAYS.between(arrivalOn, departureOn);
            for (int i = 1; i <= days; i++) {
                assertEquals(availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue(),
                        MAX_PERSONS - qtyPersons);
            }
        } catch (CustomException e) {
            assertTrue(false);
        }
    }

    @Test
    public void cancellationTest() {
        try {
            Booking booking = null;
            when(datesByBookingRepository.findAllByRangeOfDates(any(), any())).thenReturn(null);
            LocalDate date = LocalDate.now();
            LocalDate arrivalOn = date.plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = date.plus(4, ChronoUnit.DAYS);
            int qtyPersons = 8;
            when(bookingRepository.save(any())).thenReturn(getBooking(arrivalOn, departureOn, qtyPersons));
            booking = bookingService.performBooking(getCustomer(), arrivalOn,
                    departureOn, qtyPersons);
            int days = (int) DAYS.between(arrivalOn, departureOn);
            bookingService.performCancel(booking);
            for (int i = 1; i <= days; i++) {
                assertEquals(availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue(), MAX_PERSONS);
            }
        } catch (CustomException e) {
            assertTrue(false);
        }
    }

    @Test
    public void updateTest() {
        try {
            Booking booking = null;
            when(datesByBookingRepository.findAllByRangeOfDates(any(), any())).thenReturn(null);
            LocalDate date = LocalDate.now();
            LocalDate arrivalOn = date.plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = date.plus(4, ChronoUnit.DAYS);
            int days = (int) DAYS.between(arrivalOn, departureOn);
            int qtyPersons = 8;
            when(bookingRepository.save(any())).thenReturn(getBooking(arrivalOn, departureOn, qtyPersons));

            booking = bookingService.performBooking(getCustomer(), arrivalOn,
                    departureOn, qtyPersons);


            LocalDate newArrivalOn = date.plus(5, ChronoUnit.DAYS);
            LocalDate newDepartureOn = date.plus(8, ChronoUnit.DAYS);
            int newQtyPersons = 5;
            int newDays = (int) DAYS.between(newArrivalOn, newDepartureOn);
            bookingService.performUpdate(newArrivalOn, newDepartureOn, newQtyPersons, booking);

            for (int i = 1; i <= days; i++) {
                assertEquals(availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue(),
                        MAX_PERSONS);
            }
            for (int i = 5; i <= newDays; i++) {
                assertEquals(availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue(),
                        MAX_PERSONS - newQtyPersons);
            }


        } catch (NoAvailabilityException e) {
            assertTrue(false);
        } catch (CustomException e) {
            assertTrue(false);
        }
    }

    private Booking getBooking(LocalDate arrivalOn, LocalDate departureOn, int qtyPersons) {
        long id = (long) Math.random() * 10000;
        Booking booking = new Booking();
        booking.setDepartureOn(departureOn);
        booking.setArrivalOn(arrivalOn);
        booking.setCustomer(getCustomer());
        booking.setQtyPersons(qtyPersons);
        booking.setId(id);
        return booking;

    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setEmail("pepe@gmail.com");
        return customer;
    }


}
