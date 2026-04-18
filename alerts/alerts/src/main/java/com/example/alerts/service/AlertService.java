package com.example.alerts.service;

import com.example.alerts.entity.Alert;
import com.example.alerts.repository.AlertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class AlertService {
    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Page<Alert> getAlerts(Specification<Alert> spec, Pageable pageable) {
        return repository.findAll(spec, (org.springframework.data.domain.Pageable) pageable);
    }

    public Alert getAlert(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found"));
    }

    public Alert createAlert(Alert alert) {
        return repository.save(alert);
    }

    public Alert updateStatus(String id, String newStatus) {
        Alert alert = getAlert(id);
        // enforce valid transitions
        if (!isValidTransition(alert.getStatus(), newStatus)) {
            throw new IllegalArgumentException("Invalid status transition");
        }
        alert.setStatus(newStatus);
        return repository.save(alert);
    }

    private boolean isValidTransition(String current, String requested) {
        return switch (current) {
            case "NEW" -> requested.equals("UNDER_REVIEW");
            case "UNDER_REVIEW" -> requested.equals("ESCALATED") || requested.equals("CLOSED");
            case "ESCALATED" -> requested.equals("CLOSED");
            default -> false;
        };
    }
}
