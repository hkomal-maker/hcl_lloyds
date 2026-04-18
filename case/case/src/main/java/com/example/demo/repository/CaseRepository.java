package com.example.demo.repository;

import com.example.demo.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaseRepository extends JpaRepository<Case, String>, JpaSpecificationExecutor<Case> {
}
