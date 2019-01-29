package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.response.AvailabilitiesResponse;
import com.challenge.volcano.island.model.ChangeAvailabilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AvailabilitiesService {
    AvailabilitiesResponse getAvailabilities(LocalDate from, LocalDate to) throws Exception;
    Map<LocalDate, Integer> getAvailabilitiesCache();
    boolean changeAvailabilities(List<ChangeAvailabilities> changeAvailabilitiesList);
    boolean checkAvailabilities(LocalDate arrivalOn, long days, int qtyPersons);
    void refresh();
}
