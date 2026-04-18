package com.example.customer.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Customer {
    @Id
    private String customerId;

    @NotBlank
    private String name;

    @NotBlank
    private String riskRating; // HIGH, MEDIUM, LOW

    private boolean pepFlag;
    private boolean kycCompleted;

    @ElementCollection
    private List<String> accountNumbers;
}
