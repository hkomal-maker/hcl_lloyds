package com.example.alerts.controller;

import com.example.alerts.entity.Alert;
import com.example.alerts.service.AlertService;
import com.example.alerts.specification.AlertSpecifications;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Alert>> getAlerts(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String riskBand,
            @RequestParam(required = false) String alertType,
            Pageable pageable) {
        Specification<Alert> spec = AlertSpecifications.build(status, riskBand, alertType);
        return ResponseEntity.ok(service.getAlerts(spec, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlert(@PathVariable String id) {
        return ResponseEntity.ok(service.getAlert(id));
    }

    @PostMapping
    public ResponseEntity<Alert> createAlert(@Valid @RequestBody Alert alert) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAlert(alert));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Alert> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.updateStatus(id, body.get("status")));
    }
}

