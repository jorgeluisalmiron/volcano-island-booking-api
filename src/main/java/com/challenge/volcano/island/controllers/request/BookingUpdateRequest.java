package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingUpdateRequest extends BookingInfo{
    @NotNull(message = "bookingId: is required")
    private Long bookingId;

}
