package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends CustomException {

    public BookingNotFoundException() {
        super("902", "Booking not found or has expired");


    }
}
