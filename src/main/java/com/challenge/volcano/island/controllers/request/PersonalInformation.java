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

}
