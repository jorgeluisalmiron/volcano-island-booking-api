package com.challenge.volcano.island.controllers;

import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.controllers.request.MessageResponse;
import com.challenge.volcano.island.controllers.response.BookingResponse;
import com.challenge.volcano.island.exceptions.BookingNotFoundException;
import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.exceptions.NoAvailabilityException;
import com.challenge.volcano.island.exceptions.RuleException;
import com.challenge.volcano.island.model.Booking;
import com.challenge.volcano.island.services.AvailabilitiesService;
import com.challenge.volcano.island.services.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "onlinebooking", description = "Operations pertaining to bookings for Vulcano Island")
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AvailabilitiesService availabilitiesService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a booking")
    public ResponseEntity<Object> createBooking( @RequestBody BookingRequest bookingRequest) {

        try {
            BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
        } catch (NoAvailabilityException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("500", e.getMessage()));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a specific booking")
    public ResponseEntity<Object> getBooking(@PathVariable Long id) {

        try {
            Booking booking = bookingService.getBooking(id);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("500", e.getMessage() + " " + e.getCause().getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update a booking", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateBooking(@RequestBody BookingUpdateRequest bookingUpdateRequest) {

        try {
            BookingResponse bookingResponse = bookingService.updateBooking(bookingUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(bookingResponse);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (NoAvailabilityException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("500", e.getMessage()));
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Cancel a booking")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("1", e.getMessage()));
        } catch (RuleException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("500", "Error: " + e.getMessage()));
        }
    }

}
