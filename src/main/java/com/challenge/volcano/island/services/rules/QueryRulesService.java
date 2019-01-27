package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.exceptions.RuleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class QueryRulesService extends GenericRules {

    @Value("${volcano.default.availabilities-max-days}")
    private int availabilitiesMaxDays;

    public void execute(LocalDate from, LocalDate to) throws RuleException {
        this.fromDateRule(from);
        this.toDateRule(from,to);
        this.rangeMin(from);
        this.rangeMax(to);
    }

    public void rangeMin(LocalDate from) throws RuleException {
        if (from.isBefore(LocalDate.now()) || from.isEqual(LocalDate.now())){
            throw new RuleException("003", "From must be at least one day after the current day");
        }
    }
    public void rangeMax(LocalDate to) throws RuleException {
        if (to.isAfter(LocalDate.now().plus(availabilitiesMaxDays, ChronoUnit.DAYS))) {
            throw new RuleException("004", "To must be at most " + availabilitiesMaxDays+" after the current day");
        }
    }

}
