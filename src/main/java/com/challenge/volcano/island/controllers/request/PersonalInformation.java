package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonalInformation {
    @NotNull(message = "email: is required")
    @Email(message = "email: invalid email address")
    private String email;
    @NotEmpty(message = "firstName: is required")
    @Size(max=50, message = "firstName: exceed max length of 50")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "lastName: is required")
    @Size(max=50, message = "last_name: exceed max length of 50")
    @Column(name = "last_name")
    private String lastName;
    @Size(max=20)
    @Column(name = "ident_type")
    private String identificationType;
    @Size(max=20)
    @Column(name = "ident_number")
    private String identificationNum;

}
