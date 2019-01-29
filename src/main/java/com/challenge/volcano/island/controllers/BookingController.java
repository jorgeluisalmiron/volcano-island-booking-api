package com.challenge.volcano.island.controllers;

import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.controllers.response.BookingResponse;
import com.challenge.volcano.island.controllers.response.ErrorDetails;
import com.challenge.volcano.island.controllers.response.MessageResponse;
import com.challenge.volcano.island.controllers.response.OkResponse;
import com.challenge.volcano.island.controllers.utils.ErrorResponse;
import com.challenge.volcano.island.exceptions.RuleException;
import com.challenge.volcano.island.services.AvailabilitiesService;
import com.challenge.volcano.island.services.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(value = "onlinebooking", description = "Operations pertaining to bookings for Volcano Island")
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AvailabilitiesService availabilitiesService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Create a booking", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The booking was created successfully"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not Acceptable, no availability", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)

    })
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest,
                                                         BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasErrors()) {
            BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
        } else {
            throw new RuleException("001", ErrorResponse.generateError(bindingResult).getMessage());
        }
    }


    @RequestMapping(value = "/{bookingId}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get a specific booking", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Booking not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)

    })
    public ResponseEntity<BookingResponse> getBooking(@PathVariable("bookingId") Long bookingId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBooking(bookingId));
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Update a booking", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Booking not found", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not Acceptable, no availability", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)

    })
    public ResponseEntity<BookingResponse> updateBooking(@Valid @RequestBody BookingUpdateRequest bookingUpdateRequest,
                                                         BindingResult bindingResult)
            throws Exception {
        if (!bindingResult.hasErrors()) {
            BookingResponse bookingResponse = bookingService.updateBooking(bookingUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(bookingResponse);
        } else {
            throw new RuleException("001", ErrorResponse.generateError(bindingResult).getMessage());
        }

    }


    @RequestMapping(value = "/{bookingId}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Cancel a booking")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Booking not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)

    })
    public ResponseEntity<MessageResponse> delete(@PathVariable("bookingId") Long bookingId) throws Exception {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse());
    }


}
