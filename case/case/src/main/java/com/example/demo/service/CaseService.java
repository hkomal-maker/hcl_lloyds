package com.example.demo.service;

import com.example.demo.entity.AuditLogEntry;
import com.example.demo.entity.Case;
import com.example.demo.entity.CaseNote;
import com.example.demo.repository.CaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseService {
    private final CaseRepository repository;

    public CaseService(CaseRepository repository) {
        this.repository = repository;
    }

    public Case createCase(Case c) {
        c.getAuditLog().add(new AuditLogEntry(null, LocalDateTime.now(), "CASE_CREATED", "Case opened"));
        return repository.save(c);
    }

    public Case getCase(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Case not found"));
    }

    public List<Case> getCases(String status, String priority) {
        Specification<Case> spec = hasStatus(status);
        if (priority != null && !priority.isBlank()) {
            spec = spec.and(hasPriority(priority));
        }
        return repository.findAll(spec);
    }

    public Case addNote(String id, CaseNote note) {
        Case c = getCase(id);
        c.getNotes().add(note);
        c.getAuditLog().add(new AuditLogEntry(null, LocalDateTime.now(), "NOTE_ADDED", "Note added by " + note.getAuthor()));
        return repository.save(c);
    }

    public Case fileSAR(String id, String decision, String rationale) {
        Case c = getCase(id);
        if (!"PENDING_REVIEW".equals(c.getStatus())) {
            throw new IllegalStateException("SAR can only be filed from PENDING_REVIEW status");
        }
        c.setStatus("SAR_FILED");
        c.getAuditLog().add(new AuditLogEntry(null, LocalDateTime.now(), "SAR_DECISION", decision + ": " + rationale));
        return repository.save(c);
    }

    private Specification<Case> hasStatus(String status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    private Specification<Case> hasPriority(String priority) {
        return (root, query, cb) -> priority == null ? null : cb.equal(root.get("priority"), priority);
    }
}
