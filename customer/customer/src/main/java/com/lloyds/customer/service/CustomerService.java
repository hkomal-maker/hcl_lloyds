package com.lloyds.customer.service;

import com.lloyds.customer.entity.Customer;
import com.lloyds.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomer(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public List<Customer> search(String query) {
        if (query.length() < 3) {
            throw new IllegalArgumentException("Search query must be at least 3 characters");
        }
        List<Customer> byName = repository.findByNameContainingIgnoreCase(query);
        List<Customer> byAccount = repository.findByAccountNumbersContaining(query);
        return Stream.concat(byName.stream(), byAccount.stream())
                .distinct()
                .toList();
    }
}
