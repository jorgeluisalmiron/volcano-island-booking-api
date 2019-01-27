package com.challenge.volcano.island.services;

import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.model.Booking;
import com.challenge.volcano.island.model.Customer;
import com.challenge.volcano.island.repositories.BookingRepository;
import com.challenge.volcano.island.repositories.DatesByBookingRepository;
import com.challenge.volcano.island.util.MultithreadedStressTester;
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
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class BookingServicesConcurrencyTest {


    @Mock
    private DatesByBookingRepository datesByBookingRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AvailabilitiesServiceImpl availabilitiesService;

    @InjectMocks
    private BookingServiceImpl bookingService = new BookingServiceImpl();


    private Stack stackBookings = new Stack();
    private Set<String> errors = new HashSet();

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
    public void concurrencyTest()
            throws InterruptedException {
        when(datesByBookingRepository.findAllByRangeOfDates(any(), any())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(getBooking());
        MultithreadedStressTester stressTester = new MultithreadedStressTester(10000, 10);
        LocalDate date = LocalDate.now();
        stressTester.stress(() -> {
            try {
                concurrentTest();
                System.out.println(availabilitiesService.getAvailabilitiesCache().get(date.plus(1, ChronoUnit.DAYS)).intValue());
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        });
        stressTester.shutdown();
        if (!errors.isEmpty()) {
            assertTrue(errors.stream().anyMatch(e -> e.equals("No Availability") || e.equals("Booking not found")));
        }
        for (int i = 1; i <= 30; i++) {
            System.out.println(date.plus(i, ChronoUnit.DAYS) + ": " +
                    availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue());
            assertTrue(availabilitiesService.getAvailabilitiesCache().get(date.plus(i, ChronoUnit.DAYS)).intValue() >= 0);
        }
    }

     /*
        For this test I use the following probabilities:
           Bookings: 40% (with a previous query)
           Cancellations: 10%
           Updates: 20% (with a previous query)
           Queries: 30% (several queries)
     */
    private void concurrentTest() throws CustomException, InterruptedException {

        int value = (int) (Math.random() * 10);
        long days = 1 + (long) (Math.random() * 31);
        int qtyPersons = 1 + ((int) Math.random() * 9);

        //New Bookings 40%
        if (value <= 3) {
            System.out.println("New Bookings");
            Thread.sleep((long) (Math.random() * 100));
            long departureDayOffset = days > 4 ? days : 4;
            LocalDate arrivalOn = LocalDate.now().plus(departureDayOffset - 3, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(departureDayOffset, ChronoUnit.DAYS);
            availabilitiesService.performGetAvailabilities(arrivalOn,
                    departureOn);

            bookingService.performBooking(getCustomer(), arrivalOn, departureOn, qtyPersons);
            addBooking(qtyPersons, arrivalOn, departureOn);
        }

        //Cancellations 10%
        if (value == 4) {
            System.out.println("Cancellations");
            if (!stackBookings.isEmpty()) {
                Thread.sleep((long) (Math.random() * 100));
                bookingService.performCancel(popBooking());
            }

        }
        //Updates 20%
        if ((value >= 5) || (value <= 6)) {
            System.out.println("Updates");
            if (!stackBookings.isEmpty()) {
                Thread.sleep((long) (Math.random() * 100));
                long departureDayOffset = days > 4 ? days : 4;
                LocalDate arrivalOn = LocalDate.now().plus(departureDayOffset - 3, ChronoUnit.DAYS);
                LocalDate departureOn = LocalDate.now().plus(departureDayOffset, ChronoUnit.DAYS);
                availabilitiesService.performGetAvailabilities(arrivalOn,
                        departureOn);


                bookingService.performUpdate(arrivalOn, departureOn, qtyPersons, popBooking());
                addBooking(qtyPersons, arrivalOn, departureOn);
            }

        }
        //Queries 30%
        if ((value > 6)) {
            System.out.println("Queries");
            Thread.sleep((long) (Math.random() * 100));
            LocalDate date = LocalDate.now();
            long departureDayOffset = days > 4 ? days : 4;

            for (int i = 0; i < 10; i++) {
                availabilitiesService.performGetAvailabilities(date.plus(departureDayOffset - 3, ChronoUnit.DAYS),
                        date.plus(departureDayOffset, ChronoUnit.DAYS));
                departureDayOffset++;
            }
        }
    }

    private void addBooking(int qtyPersons, LocalDate arrivalOn, LocalDate departureOn) {
        Booking booking = new Booking();
        booking.setId((long) Math.random() * 10000);
        booking.setStatus("booked");
        booking.setArrivalOn(arrivalOn);
        booking.setDepartureOn(departureOn);
        booking.setQtyPersons(qtyPersons);
        stackBookings.push(booking);
    }

    private Booking getBooking() {
        long id = (long) Math.random() * 10000;
        Booking booking = new Booking();
        booking.setDepartureOn(LocalDate.now().plus(3, ChronoUnit.DAYS));
        booking.setArrivalOn(LocalDate.now().plus(1, ChronoUnit.DAYS));
        booking.setCustomer(getCustomer());
        booking.setId(id);
        return booking;

    }

    private synchronized Booking popBooking() {
        if (!stackBookings.empty()) {
            return (Booking) stackBookings.pop();
        }
        return getBooking();
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setEmail("pepe@gmail.com");
        return customer;
    }


}
