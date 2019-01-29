package com.challenge.volcano.island.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse extends MessageResponse {
    private BookingInfoResponse bookingInfo;

    public BookingResponse(){
        super("000","Success");
    }

    public BookingResponse(String code, String message){
        super(code,message);
    }
}
