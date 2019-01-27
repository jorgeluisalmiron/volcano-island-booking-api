package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.request.AvailabilitiesResponse;
import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.model.ChangeAvailabilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AvailabilitiesService {
    AvailabilitiesResponse getAvailabilities(LocalDate from, LocalDate to) throws CustomException;
    AvailabilitiesResponse getAvailabilities();
    Map<LocalDate, Integer> getAvailabilitiesCache();
    boolean changeAvailabilities(List<ChangeAvailabilities> changeAvailabilitiesList);
    boolean checkAvailabilities(LocalDate arrivalOn, long days, int qtyPersons);
    void refresh();
}
