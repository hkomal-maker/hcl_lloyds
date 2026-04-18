package com.example.customer.repository;

import com.example.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByAccountNumbersContaining(String accountNumber);
}

