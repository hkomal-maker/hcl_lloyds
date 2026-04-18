package com.ll.lbe.alert_service.controller;

import com.ll.lbe.alert_service.model.Alert;
import com.ll.lbe.alert_service.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public Page<Alert> getAlerts(@RequestParam Map<String, String> filters, Pageable pageable) {
        return alertService.getAlerts(filters, pageable);
    }

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable Long id) {
        return alertService.getAlertById(id);
    }

    @PostMapping
    public Alert createAlert(@Valid @RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    @PatchMapping("/{id}/status")
    public Alert updateStatus(@PathVariable Long id, @RequestParam String status) {
        return alertService.updateStatus(id, status);
    }

    @GetMapping("/summary")
    public Map<String, Long> getSummary(@RequestParam(defaultValue = "status") String groupBy) {
        return alertService.getSummary(groupBy);
    }
}
