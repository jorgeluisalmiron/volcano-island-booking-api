package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonalInformation {
    @Email(message = "Please provide valid email address")
    @Size(max=50)
    private String email;
    @NotEmpty(message="first_name is a required field")
    @Size(max=50)
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message="last_name is a required field")
    @Size(max=50)
    @Column(name = "last_name")
    private String lastName;
    @Size(max=20)
    @Column(name = "ident_type")
    private String identificationType;
    @Size(max=20)
    @Column(name = "ident_number")
    private String identificationNum;
    @Size(max=100)
    private String address;
    @Size(max=50)
    private String city;
    @Size(max=50)
    private String country;
    @Size(max=50)
    private String state;
    @Size(max=15)
    private String zip;
    @Size(max=20)
    private String phone;
}
