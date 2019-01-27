package com.challenge.volcano.island.controllers.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class AvailabilitiesResponse {

    private Map<LocalDate, Integer> availabilities;
    public void setAvailabilities(Map<LocalDate, Integer> availabilities){
        this.availabilities=availabilities;
   }

}
