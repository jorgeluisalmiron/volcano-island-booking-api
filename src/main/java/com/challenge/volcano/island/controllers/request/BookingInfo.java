package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class BookingInfo {
    @NotNull
    @Future
    private LocalDate arrivalOn;
    @NotNull
    @Future
    private LocalDate departureOn;
    @NotNull
    @Min(1)
    private int qtyPersons;

}
