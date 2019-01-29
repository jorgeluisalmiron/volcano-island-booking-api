package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.controllers.request.BookingInfo;
import com.challenge.volcano.island.exceptions.NoAvailabilityException;
import com.challenge.volcano.island.exceptions.RuleException;
import com.challenge.volcano.island.model.Rule;
import com.challenge.volcano.island.services.AvailabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class BookingRulesService extends GenericRules {

    @Autowired
    private RulesService rulesService;

    @Autowired
    private AvailabilitiesService availabilitiesService;


    public void execute(BookingInfo bookingInfo) throws Exception {
        fromDateRule(bookingInfo.getArrivalOn());
        toDateRule(bookingInfo.getArrivalOn(),bookingInfo.getDepartureOn());
        qtyPersonsRule(bookingInfo.getQtyPersons());
        stayRule(bookingInfo.getArrivalOn(), bookingInfo.getDepartureOn());
        reservedDaysRule(bookingInfo.getArrivalOn(), bookingInfo.getDepartureOn());
        availabilityRule(bookingInfo.getArrivalOn(), bookingInfo.getDepartureOn(), bookingInfo.getQtyPersons());
    }


    public void stayRule(LocalDate arrivalOn, LocalDate departureOn) throws RuleException {
        Rule rule = rulesService.getRule("STAY_RULE");
        if (Objects.nonNull(rule)) {
            long diff = DAYS.between(arrivalOn, departureOn);
            if (diff < rule.getMin() || diff > rule.getMax()) {
                StringBuilder message = new StringBuilder()
                        .append("The stay must be between ")
                        .append(rule.getMin())
                        .append(" and ")
                        .append(rule.getMax())
                        .append(" days. ")
                        .append("The stay requested is ")
                        .append(diff)
                        .append(" days.");

                throw new RuleException("100", message.toString());
            }
        }
    }

    public void reservedDaysRule(LocalDate arrivalOn, LocalDate departureOn) throws RuleException {

        Rule rule = rulesService.getRule("RESERVED_DAYS_RULE");

        if (Objects.nonNull(rule)) {
            long diffArrival = DAYS.between(LocalDate.now(), arrivalOn);
            long diffDeparture = DAYS.between(LocalDate.now(), departureOn);
            if (diffArrival < rule.getMin() || diffArrival > rule.getMax() || (diffDeparture < rule.getMin()) || diffDeparture > rule.getMax()) {
                StringBuilder message = new StringBuilder()
                        .append("The arrival and departure must be between ")
                        .append(rule.getMin())
                        .append(" and ")
                        .append(rule.getMax())
                        .append(" days from today.");

                throw new RuleException("101", message.toString());
            }
        }
    }


    void availabilityRule(LocalDate arrivalOn, LocalDate departureOn, int qtyPersons) throws NoAvailabilityException {
        long days = DAYS.between(arrivalOn, departureOn);
        boolean fullAvailability = availabilitiesService.checkAvailabilities(arrivalOn, days, qtyPersons);
        if (!fullAvailability){
            throw new NoAvailabilityException();
        }

    }


    void qtyPersonsRule(int qtyPersons) throws RuleException {
        Rule rule = rulesService.getRule("PERSONS_PER_BOOKING_RULE");
        if ((qtyPersons<rule.getMin()) || (qtyPersons>rule.getMax())){

            StringBuilder message = new StringBuilder()
                    .append("Quantity of persons must be between ")
                    .append(rule.getMin())
                    .append(" and ")
                    .append(rule.getMax());

            throw new RuleException("103", message.toString());
        }
    }
}
