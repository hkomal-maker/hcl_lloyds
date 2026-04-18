package com.lloyds.alerts.repository;

import com.lloyds.alerts.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlertRepository extends JpaRepository<Alert, String>, JpaSpecificationExecutor<Alert> {
}

