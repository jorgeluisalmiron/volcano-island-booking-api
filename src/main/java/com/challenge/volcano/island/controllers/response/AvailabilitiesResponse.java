package com.challenge.volcano.island.controllers.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class AvailabilitiesResponse extends MessageResponse{

    public AvailabilitiesResponse(){
        super("000","Success");
    }

    public AvailabilitiesResponse(String code, String message){
        super(code,message);
    }
    private Map<LocalDate, Integer> availabilities;
    public void setAvailabilities(Map<LocalDate, Integer> availabilities){
        this.availabilities=availabilities;
   }

}
