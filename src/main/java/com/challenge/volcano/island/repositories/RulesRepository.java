package com.challenge.volcano.island.repositories;

import com.challenge.volcano.island.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RulesRepository extends JpaRepository<Rule, String> {

}
