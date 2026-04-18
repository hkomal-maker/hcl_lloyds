package com.example.demo.controller;

import com.example.demo.entity.Case;
import com.example.demo.entity.CaseNote;
import com.example.demo.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService service;

    public CaseController(CaseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Case> createCase(@Valid @RequestBody Case c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCase(c));
    }

    @GetMapping
    public ResponseEntity<List<Case>> getCases(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {
        return ResponseEntity.ok(service.getCases(status, priority));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Case> getCase(@PathVariable String id) {
        return ResponseEntity.ok(service.getCase(id));
    }

    @PostMapping("/{id}/notes")
    public ResponseEntity<Case> addNote(@PathVariable String id, @Valid @RequestBody CaseNote note) {
        return ResponseEntity.ok(service.addNote(id, note));
    }

    @PostMapping("/{id}/sar")
    public ResponseEntity<Case> fileSAR(@PathVariable String id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.fileSAR(id, body.get("decision"), body.get("rationale")));
    }
}
