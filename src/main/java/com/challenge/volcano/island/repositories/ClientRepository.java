package com.challenge.volcano.island.repositories;

import com.challenge.volcano.island.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
