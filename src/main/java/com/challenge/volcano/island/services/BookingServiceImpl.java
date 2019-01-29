package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.controllers.response.BookingResponse;
import com.challenge.volcano.island.exceptions.BookingNotFoundException;
import com.challenge.volcano.island.exceptions.NoAvailabilityException;
import com.challenge.volcano.island.mappers.BookingMapper;
import com.challenge.volcano.island.mappers.BookingResponseMapper;
import com.challenge.volcano.island.model.Booking;
import com.challenge.volcano.island.model.ChangeAvailabilities;
import com.challenge.volcano.island.model.Customer;
import com.challenge.volcano.island.model.DatesByBooking;
import com.challenge.volcano.island.repositories.BookingRepository;
import com.challenge.volcano.island.repositories.DatesByBookingRepository;
import com.challenge.volcano.island.services.rules.BookingRulesService;
import com.challenge.volcano.island.services.rules.CancellationRulesService;
import com.challenge.volcano.island.services.rules.UpdatingBookingRulesService;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookingRulesService bookingRulesService;

    @Autowired
    private CancellationRulesService cancellationRulesService;


    @Autowired
    private UpdatingBookingRulesService updatingBookingRulesService;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private AvailabilitiesService availabilitiesService;

    @Autowired
    private BookingResponseMapper bookingResponseMapper;

    @Autowired
    private DatesByBookingRepository datesByBookingRepository;


    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) throws Exception{
        bookingRulesService.execute(bookingRequest);
        Customer customer  = customerService.getCustomer(bookingRequest.getPersonalInformation());
        Booking booking = performBooking(customer,bookingRequest.getArrivalOn(),
                bookingRequest.getDepartureOn(),bookingRequest.getQtyPersons());
        BookingResponse bookingResponse = bookingResponseMapper.bookingToBookingResponse(booking);
        return bookingResponse;
    }


    @Override
    public BookingResponse updateBooking(BookingUpdateRequest bookingUpdateRequest) throws Exception {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingUpdateRequest.getBookingId());
        if (!optionalBooking.isPresent()){
            throw new BookingNotFoundException();
        }
        Booking booking = optionalBooking.get();
        updatingBookingRulesService.execute(booking, bookingUpdateRequest);
        performUpdate(bookingUpdateRequest.getArrivalOn(),
                bookingUpdateRequest.getDepartureOn(),
                bookingUpdateRequest.getQtyPersons(), booking);
        BookingResponse bookingResponse = bookingResponseMapper.bookingToBookingResponse(booking);
        return bookingResponse;
    }

    @VisibleForTesting
    protected void performUpdate(LocalDate newArrivalOn, LocalDate newDepartureOn, int newQtyPersons,
                               Booking booking) throws NoAvailabilityException {
        LocalDate arrivalOn = booking.getArrivalOn();
        LocalDate departureOn = booking.getDepartureOn();
        long days = DAYS.between(arrivalOn, departureOn);
        List<ChangeAvailabilities> changeAvailabilitiesList = new ArrayList<>();

        ChangeAvailabilities freeAvailabilities = new ChangeAvailabilities(
                arrivalOn, days, booking.getQtyPersons() * -1);

        changeAvailabilitiesList.add(freeAvailabilities);

        long newDays = DAYS.between(newArrivalOn, newDepartureOn);

        ChangeAvailabilities reserveAvailabilities = new ChangeAvailabilities(
                newArrivalOn, newDays, newQtyPersons);

        changeAvailabilitiesList.add(reserveAvailabilities);

        boolean availChanged = availabilitiesService.changeAvailabilities(changeAvailabilitiesList);
        if (!availChanged) {
            throw new NoAvailabilityException();
        }

        booking.getDatesByBookings().clear();
        booking.setQtyPersons(newQtyPersons);
        booking.setDepartureOn(newDepartureOn);
        booking.setArrivalOn(newArrivalOn);
        addDatesByBooking(booking.getDatesByBookings(),newArrivalOn, newDays);
        bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long id) throws Exception {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (!booking.isPresent()){
            throw new BookingNotFoundException();
        }
        cancellationRulesService.execute(booking.get());
        performCancel(booking.get());
    }



    @Override
    public BookingResponse getBooking(Long id) throws Exception {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (!booking.isPresent()){
            throw new BookingNotFoundException();
        }
        BookingResponse bookingResponse = bookingResponseMapper.bookingToBookingResponse(booking.get());
        return bookingResponse;
    }

    @VisibleForTesting
    protected void performCancel(Booking booking){
        long days = DAYS.between(booking.getArrivalOn(),booking.getDepartureOn());
        ChangeAvailabilities changeAvailabilities = new ChangeAvailabilities(
                booking.getArrivalOn(), days, booking.getQtyPersons() * -1);
        List<ChangeAvailabilities> changeAvailabilitiesList = new ArrayList<>();
        changeAvailabilitiesList.add(changeAvailabilities);
        availabilitiesService.changeAvailabilities(changeAvailabilitiesList);
        booking.getDatesByBookings().clear();
        booking.setStatus("cancelled");
        bookingRepository.save(booking);
    }

    @VisibleForTesting
    protected Booking performBooking(Customer customer, LocalDate arrivalOn, LocalDate departureOn, int qtyPersons)
            throws Exception {
        Booking booking = new Booking();
        booking.setQtyPersons(qtyPersons);
        booking.setArrivalOn(arrivalOn);
        booking.setDepartureOn(departureOn);
        booking.setCustomer(customer);
        long days = DAYS.between(arrivalOn, departureOn);
        addDatesByBooking(booking.getDatesByBookings(),arrivalOn, days);
        ChangeAvailabilities changeAvailabilities = new ChangeAvailabilities(
                arrivalOn, days, qtyPersons);
        List<ChangeAvailabilities> changeAvailabilitiesList = new ArrayList<>();
        changeAvailabilitiesList.add(changeAvailabilities);
        boolean availChanged = availabilitiesService.changeAvailabilities(changeAvailabilitiesList);
        if (!availChanged) {
            throw new NoAvailabilityException();
        }
        booking.setStatus("booked");
        return bookingRepository.save(booking);

    }

    private void addDatesByBooking(List<DatesByBooking> occupanciesList, LocalDate arrivalOn, long days) {
        for (int i=0; i<days; i++){
            LocalDate date = arrivalOn.plus(i, ChronoUnit.DAYS);
            DatesByBooking datesByBooking = new DatesByBooking(date);
            occupanciesList.add(datesByBooking);
        }
    }

}
