package com.challenge.volcano.island.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "rules")
public class Rule {
    @Id
    private String name;
    private int min;
    private int max;
}
