package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingUpdateRequest extends BookingInfo{
    @NotNull
    private Long id;
    @NotNull
    @Email
    private String email;
}
