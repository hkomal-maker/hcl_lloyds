package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String noteId;

    @NotBlank
    @Size(min = 10, max = 2000)
    private String text;

    @NotBlank
    private String author;

    private LocalDateTime createdAt = LocalDateTime.now();
}
