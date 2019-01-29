package com.challenge.volcano.island.controllers.handlers;

import com.challenge.volcano.island.controllers.response.ErrorDetails;
import com.challenge.volcano.island.exceptions.BookingNotFoundException;
import com.challenge.volcano.island.exceptions.NoAvailabilityException;
import com.challenge.volcano.island.exceptions.RuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuleException.class)
    public ResponseEntity<?> ruleException(RuleException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getCode(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAvailabilityException.class)
    public ResponseEntity<?> noAvailabilityException(NoAvailabilityException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getCode(),ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> bookingNotFoundException(BookingNotFoundException ex, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getCode(),ex.getMessage(),
                    request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "500", ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}