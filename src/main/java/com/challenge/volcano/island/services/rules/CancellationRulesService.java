package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.model.Booking;
import org.springframework.stereotype.Service;


@Service
public class CancellationRulesService extends GenericRules {


    public void execute(Booking booking) throws Exception {
        this.fromDateRule(booking.getArrivalOn(),"200", "Could not cancel an expired booking");
    }



}
