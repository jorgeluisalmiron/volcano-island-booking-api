package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.model.Rule;
import com.challenge.volcano.island.repositories.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RuleServiceImpl implements RulesService {

    @Autowired
    private RulesRepository rulesRepository;

    private Map<String,Rule> ruleMap;

    @PostConstruct
    public void initializeList(){
        this.ruleMap = rulesRepository.findAll().stream().collect(
                Collectors.toMap(Rule::getName, rule->rule));
    }

    @Override
    public Rule getRule(String id) {
        return this.ruleMap.get(id);
    }
}
