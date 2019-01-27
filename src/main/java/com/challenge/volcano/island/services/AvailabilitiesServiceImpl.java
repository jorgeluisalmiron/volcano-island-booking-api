package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.request.AvailabilitiesResponse;
import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.model.ChangeAvailabilities;
import com.challenge.volcano.island.model.DatesByBooking;
import com.challenge.volcano.island.repositories.DatesByBookingRepository;
import com.challenge.volcano.island.services.rules.QueryRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;



@Service
public class AvailabilitiesServiceImpl implements AvailabilitiesService {

    @Autowired
    private DatesByBookingRepository datesByBookingRepository;

    @Autowired
    private QueryRulesService queryRulesService;

    private ConcurrentMap<LocalDate,Integer> availabilities=new ConcurrentSkipListMap<>();

    @Value("${volcano.default.max-persons-per-day}")
    private int maxPersons;

    @Value("${volcano.default.availabilities-max-days}")
    private int availabilitiesMaxDays;

    @Override
    public AvailabilitiesResponse getAvailabilities(LocalDate from, LocalDate to) throws CustomException {
        Map<LocalDate, Integer> responseMap;
        if (Objects.isNull(from) && Objects.isNull(to)) {
            responseMap = getAvailabilitiesCache();

        } else {
            if (Objects.isNull(from)) {
                from = LocalDate.now().plus(1, ChronoUnit.DAYS);
            }
            if (Objects.isNull(to)) {
                to = LocalDate.now().plus(availabilitiesMaxDays, ChronoUnit.DAYS);
            }

            queryRulesService.execute(from, to);
            responseMap = performGetAvailabilities(from, to);
        }
        AvailabilitiesResponse availabilitiesResponse = new AvailabilitiesResponse();
        availabilitiesResponse.setAvailabilities(responseMap);
        return availabilitiesResponse;

    }

    public Map<LocalDate, Integer> performGetAvailabilities(LocalDate from, LocalDate to) {
        LocalDate limit1 = from.minus(1, ChronoUnit.DAYS);
        LocalDate limit2 = to.plus(1, ChronoUnit.DAYS);
        return this.getAvailabilitiesCache().entrySet().stream()
                .filter(m-> m.getKey().isAfter(limit1) && m.getKey().isBefore(limit2))
                .collect(Collectors.toMap(x -> x.getKey(), x->x.getValue()));
    }

    @Override
    public Map<LocalDate, Integer> getAvailabilitiesCache() {
        if (this.availabilities.isEmpty()){
            this.refresh();
        }
        return this.availabilities;
    }

    @Override
    public synchronized boolean changeAvailabilities(List<ChangeAvailabilities> changeAvailabilitiesList) {
        int size = changeAvailabilitiesList.size();
        int i = 0;
        boolean allAvailChecked = true;
        while ( i<size  && allAvailChecked) {
             allAvailChecked = this.changeAvailability(changeAvailabilitiesList.get(i));
             changeAvailabilitiesList.get(i).setAvailChecked(allAvailChecked);
             i++;
        }
        if (!allAvailChecked){
            rollback(changeAvailabilitiesList);
        }

        return allAvailChecked;
    }

    private void rollback(List<ChangeAvailabilities> changeAvailabilitiesList) {
        List<ChangeAvailabilities> rollbackList = changeAvailabilitiesList.stream().
                filter(c -> c.isAvailChecked()).peek(c -> c.setQtyPersons(c.getQtyPersons()*-1)).
                collect(Collectors.toList());
        rollbackList.stream().forEach(c->this.changeAvailability(c));
    }

    public synchronized void refresh() {
        for (int i=1; i<=availabilitiesMaxDays; i++){
            availabilities.put(LocalDate.now().plus(i, ChronoUnit.DAYS),maxPersons);
        }
        List<DatesByBooking> datesByBookingList = datesByBookingRepository.findAllByRangeOfDates
                (LocalDate.now().plus(1, ChronoUnit.DAYS),LocalDate.now().plus(availabilitiesMaxDays, ChronoUnit.DAYS));

        if (Objects.nonNull(datesByBookingList)) {
            datesByBookingList.stream()
                    .collect(Collectors.groupingBy(DatesByBooking::getDate, Collectors.summingInt(DatesByBooking::getBookingQtyPersons)))
                    .entrySet().stream().forEach(m -> {
                        if (availabilities.containsKey(m.getKey())) {
                            availabilities.put(m.getKey(), availabilities.get(m.getKey()) - m.getValue());
                        }
                    }

            );
        }
    }

    public  boolean changeAvailability(ChangeAvailabilities changeAvailabilities) {
        LocalDate arrivalOn = changeAvailabilities.getArrivalOn();
        long days = changeAvailabilities.getDays();
        int qtyPersons = changeAvailabilities.getQtyPersons();
        if (this.availabilities.isEmpty()){
            this.refresh();
        }
        Map<LocalDate, Integer> newAvailabilities = getNewAvailabilities(arrivalOn, days, qtyPersons);
        boolean fullAvailability = !newAvailabilities.isEmpty() && !(newAvailabilities.entrySet().stream().anyMatch(m->m.getValue()<0));
        if (fullAvailability)
        {
            newAvailabilities.entrySet().stream().forEach(m->this.availabilities.put(m.getKey(),m.getValue()));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAvailabilities(LocalDate arrivalOn, long days, int qtyPersons){
        Map<LocalDate, Integer> newAvailabilities = getNewAvailabilities(arrivalOn, days, qtyPersons);
        boolean fullAvailability = !(newAvailabilities.entrySet().stream().anyMatch(m->m.getValue()<0));
        return fullAvailability;
    }

    private Map<LocalDate, Integer> getNewAvailabilities(LocalDate arrivalOn, long days, int qtyPersons) {
        Map<LocalDate, Integer> newAvailabilities = new Hashtable<>();
        for (int i = 0; i < days; i++) {
            LocalDate date = arrivalOn.plus(i, ChronoUnit.DAYS);
            if (this.availabilities.containsKey(date)) {
                int newAvailability = this.availabilities.get(date) - qtyPersons;
                newAvailabilities.put(date, newAvailability);

            }
        }
        return newAvailabilities;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int maxPersons) {
        this.maxPersons = maxPersons;
    }

    public int getAvailabilitiesMaxDays() {
        return availabilitiesMaxDays;
    }

    public void setAvailabilitiesMaxDays(int availabilitiesMaxDays) {
        this.availabilitiesMaxDays = availabilitiesMaxDays;
    }
}
