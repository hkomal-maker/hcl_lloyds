package com.example.alerts.repository;

import com.example.alerts.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlertRepository extends JpaRepository<Alert, String>, JpaSpecificationExecutor<Alert> {
}

