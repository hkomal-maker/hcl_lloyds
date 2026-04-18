package com.ll.lbe.alert_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @NotBlank(message = "Customer ID must not be blank")
    private String customerId;

    @NotBlank(message = "Must be one of the valid alert types")
    @Pattern(regexp = "STRUCTURING|APP_SCAM|SANCTIONS_HIT|UNUSUAL_ACTIVITY|ACCOUNT_TAKEOVER")
    private String alertType;

    @NotNull(message = "Risk band must be HIGH, MEDIUM, or LOW")
    @Pattern(regexp = "HIGH|MEDIUM|LOW")
    private String riskBand;

    @NotNull(message = "Amount must be greater than 0")
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotBlank(message = "Currency must be a 3-character ISO code")
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull(message = "Triggered date must not be in the future")
    @PastOrPresent
    private LocalDateTime triggeredAt;

    @NotEmpty(message = "At least one flagged rule must be provided")
    @ElementCollection
    private List<String> flaggedRules;

    private String status = "NEW";
    private String assignedAnalyst;
}
