package com.ll.lbe.alert_service.service;

import com.ll.lbe.alert_service.common.exception.GlobalExceptionHandler;
import com.ll.lbe.alert_service.dao.AlertRepository;
import com.ll.lbe.alert_service.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Page<Alert> getAlerts(Map<String, String> filters, Pageable pageable) {
        // Implement filtering logic using JPA Specifications or custom queries
        return repository.findAll(pageable);
    }

    public Alert getAlertById(Long id) {
        return repository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Alert not found"));
    }

    public Alert createAlert(Alert alert) {
        return repository.save(alert);
    }

    public Alert updateStatus(Long id, String status) {
        Alert alert = getAlertById(id);
        // Validate transitions NEW → UNDER_REVIEW → ESCALATED → CLOSED
        List<String> validStatuses = List.of("NEW", "UNDER_REVIEW", "ESCALATED", "CLOSED");
        String currentStatus = alert.getStatus();
        if (!validStatuses.contains(status)) {
            throw new GlobalExceptionHandler.BusinessRuleException("Invalid status: " + status);
        }
        int currentIndex = validStatuses.indexOf(currentStatus);
        int newIndex = validStatuses.indexOf(status);
        if (newIndex < currentIndex) {
            throw new GlobalExceptionHandler.BusinessRuleException("Invalid status transition from " + currentStatus + " to " + status);
        }
        alert.setStatus(status);
        return repository.save(alert);
    }

    public Map<String, Long> getSummary(String groupBy) {
        switch (groupBy.toLowerCase()) {
            case "status":
                return repository.findAll().stream()
                        .collect(Collectors.groupingBy(Alert::getStatus, Collectors.counting()));

            case "riskband":
                return repository.findAll().stream()
                        .collect(Collectors.groupingBy(Alert::getRiskBand, Collectors.counting()));

            case "alerttype":
                return repository.findAll().stream()
                        .collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()));

            default:
                throw new GlobalExceptionHandler.BusinessRuleException(
                        "groupBy must be one of: status, riskBand, alertType"
                );
        }
    }
}
