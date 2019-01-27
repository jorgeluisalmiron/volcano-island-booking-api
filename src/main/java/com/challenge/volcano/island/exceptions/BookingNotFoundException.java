package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends CustomException{

    private String code;
    public BookingNotFoundException(){
        super("902","Booking not found");
        this.code = "902";

    }
}
