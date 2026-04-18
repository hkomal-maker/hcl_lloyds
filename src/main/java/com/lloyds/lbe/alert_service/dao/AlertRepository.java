package com.lloyds.lbe.alert_service.dao;

import com.lloyds.lbe.alert_service.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {}
