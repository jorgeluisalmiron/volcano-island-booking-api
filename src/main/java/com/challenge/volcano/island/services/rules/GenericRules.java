package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.exceptions.RuleException;

import java.time.LocalDate;

public class GenericRules {
    protected void fromDateRule(LocalDate from) throws RuleException {
        LocalDate now = LocalDate.now();
        if (now.isAfter(from)){
            throw new RuleException("002","Invalid Arrival Date");
        }
    }
    protected void toDateRule(LocalDate from, LocalDate to) throws RuleException {
        if (!to.isAfter(from)){
            throw new RuleException("003","Invalid Departure Date");
        }
    }
    protected void fromDateRule(LocalDate from,String code, String message) throws RuleException {
        LocalDate now = LocalDate.now();
        if (now.isAfter(from)){
            throw new RuleException(code,message);
        }
    }

}
