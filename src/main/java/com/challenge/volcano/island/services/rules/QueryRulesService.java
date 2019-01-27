package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.exceptions.RuleException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QueryRulesService extends GenericRules {

    public void execute(LocalDate from, LocalDate to) throws RuleException {
        this.fromDateRule(from);
        this.toDateRule(from,to);
    }

}
