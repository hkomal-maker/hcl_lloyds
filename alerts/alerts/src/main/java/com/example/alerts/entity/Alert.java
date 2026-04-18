package com.example.alerts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String alertId;

    @NotBlank
    private String customerId;

    @NotBlank
    private String alertType; // STRUCTURING, APP_SCAM, SANCTIONS_HIT, UNUSUAL_ACTIVITY, ACCOUNT_TAKEOVER

    @NotBlank
    private String riskBand; // HIGH, MEDIUM, LOW

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull
    @PastOrPresent
    private LocalDateTime triggeredAt;

    @NotBlank
    private String status; // NEW, UNDER_REVIEW, ESCALATED, CLOSED

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getRiskBand() {
        return riskBand;
    }

    public void setRiskBand(String riskBand) {
        this.riskBand = riskBand;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }

    public void setTriggeredAt(LocalDateTime triggeredAt) {
        this.triggeredAt = triggeredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedAnalyst() {
        return assignedAnalyst;
    }

    public void setAssignedAnalyst(String assignedAnalyst) {
        this.assignedAnalyst = assignedAnalyst;
    }

    public List<String> getFlaggedRules() {
        return flaggedRules;
    }

    public void setFlaggedRules(List<String> flaggedRules) {
        this.flaggedRules = flaggedRules;
    }

    private String assignedAnalyst;

    @ElementCollection
    @NotEmpty
    private List<String> flaggedRules;
}
