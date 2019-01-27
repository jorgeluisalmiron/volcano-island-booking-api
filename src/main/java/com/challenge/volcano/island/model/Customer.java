package com.challenge.volcano.island.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name="client_generator", sequenceName = "client_seq", initialValue = 100, allocationSize = 1)
    private Long id;
    @Email
    private String email;
    @NotNull
    @Size(max=50)
    @Column(name = "first_name")
    private String firstName;
    @Size(max=50)
    @Column(name = "last_name")
    private String lastName;
    @Size(max=20)
    @Column(name = "ident_type")
    private String identificationType;
    @Size(max=20)
    @Column(name = "ident_number")
    private String identificationNum;

}
