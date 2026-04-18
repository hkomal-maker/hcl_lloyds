package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String caseId;

    @NotBlank
    private String customerId;

    @NotEmpty
    @ElementCollection
    private List<String> linkedAlertIds;

    @NotBlank
    private String priority; // HIGH, MEDIUM, LOW

    @NotBlank
    private String assignedAnalyst;

    @NotBlank
    private String status; // OPEN → UNDER_INVESTIGATION → PENDING_REVIEW → SAR_FILED / NO_ACTION_TAKEN / CLOSED

    private LocalDateTime openedAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaseNote> notes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLogEntry> auditLog = new ArrayList<>();
}

