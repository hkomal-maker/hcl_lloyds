package com.lloyds.customer.controller;

import com.lloyds.customer.entity.Customer;
import com.lloyds.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> search(@RequestParam String q) {
        return ResponseEntity.ok(service.search(q));
    }

    @GetMapping("/{id}/alerts")
    public ResponseEntity<List<String>> getCustomerAlerts(@PathVariable String id) {
        // Placeholder: integrate with Alert Service via REST client
        return ResponseEntity.ok(List.of("Alert1", "Alert2"));
    }
}
