package com.challenge.volcano.island.controllers.response;

import com.challenge.volcano.island.controllers.request.BookingInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse extends BookingInfo {
    private Long bookingId;
}
