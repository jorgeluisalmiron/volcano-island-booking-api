package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingUpdateRequest extends BookingInfo{
    @NotNull
    private Long id;
    @NotNull
    private String email;
}
