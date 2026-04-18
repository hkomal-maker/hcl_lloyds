package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String auditId;

    private LocalDateTime timestamp = LocalDateTime.now();
    private String action;      // e.g. STATUS_CHANGE, NOTE_ADDED, SAR_DECISION
    private String details;     // description of what changed
}
