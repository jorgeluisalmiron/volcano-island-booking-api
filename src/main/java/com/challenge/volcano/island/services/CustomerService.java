package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.request.PersonalInformation;
import com.challenge.volcano.island.model.Customer;

public interface CustomerService {
    Customer findByEmail(String email);
    Customer getCustomer(PersonalInformation personalInformation);
}
