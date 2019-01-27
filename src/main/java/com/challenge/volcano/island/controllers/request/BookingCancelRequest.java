package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCancelRequest {
    private Long id;
    private String email;
}
