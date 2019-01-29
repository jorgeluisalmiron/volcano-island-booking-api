package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends Exception {

    private static final String BOOKING_NOT_FOUND_OR_HAS_EXPIRED = "Booking not found or has expired";
    private String code;
    private String message;
    public BookingNotFoundException() {
        super(BOOKING_NOT_FOUND_OR_HAS_EXPIRED);
        this.code = "404";
        this.message = BOOKING_NOT_FOUND_OR_HAS_EXPIRED;
    }
}
