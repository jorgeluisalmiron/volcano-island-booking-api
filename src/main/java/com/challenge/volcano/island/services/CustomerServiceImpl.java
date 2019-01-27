package com.challenge.volcano.island.services;

import com.challenge.volcano.island.controllers.request.PersonalInformation;
import com.challenge.volcano.island.mappers.CustomerMapper;
import com.challenge.volcano.island.model.Customer;
import com.challenge.volcano.island.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public Customer findByEmail (String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Customer getCustomer(PersonalInformation personalInformation) {
        Customer customer = findByEmail(personalInformation.getEmail());
        if (Objects.isNull(customer)){
            return clientRepository.save(this.customerMapper.personalInformationToCustomer(personalInformation));
        }
        return customer;
    }
}
