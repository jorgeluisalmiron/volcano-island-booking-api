package com.challenge.volcano.island.controllers.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {
    private Long bookingId;
    private LocalDate arrivalOn;
    private LocalDate departureOn;
    private int qtyPersons;

}
