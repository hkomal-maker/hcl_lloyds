package com.lloyds.lbe.alert_service.service;

import com.lloyds.lbe.alert_service.common.exception.GlobalExceptionHandler;
import com.lloyds.lbe.alert_service.dao.AlertRepository;
import com.lloyds.lbe.alert_service.model.Alert;
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
        // Supported groupBy fields
        List<String> validGroupBy = List.of("status", "alertType", "riskBand");
        if (!validGroupBy.contains(groupBy)) {
            throw new GlobalExceptionHandler.BusinessRuleException("Invalid groupBy parameter: " + groupBy);
        }

        List<Alert> alerts = repository.findAll();

        Map<String, Long> summary = alerts.stream()
                .collect(Collectors.groupingBy(alert -> {
                    switch (groupBy) {
                        case "alertType":
                            return alert.getAlertType();
                        case "riskBand":
                            return alert.getRiskBand();
                        default:
                            return alert.getStatus();
                    }
                }, Collectors.counting()));

        return summary;
    }
}
