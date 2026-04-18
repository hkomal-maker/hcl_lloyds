package com.example.alerts.specification;

import com.example.alerts.entity.Alert;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class AlertSpecifications {

    public static Specification<Alert> build(String status, String riskBand, String alertType) {
        return Specification.where(hasStatus(status))
                .and(hasRiskBand(riskBand))
                .and(hasAlertType(alertType));
    }

    public static Specification<Alert> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank()) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Alert> hasRiskBand(String riskBand) {
        return (root, query, cb) -> {
            if (riskBand == null || riskBand.isBlank()) {
                return null;
            }
            return cb.equal(root.get("riskBand"), riskBand);
        };
    }

    public static Specification<Alert> hasAlertType(String alertType) {
        return (root, query, cb) -> {
            if (alertType == null || alertType.isBlank()) {
                return null;
            }
            return cb.equal(root.get("alertType"), alertType);
        };
    }

    public static Specification<Alert> amountBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) {
                return null;
            } else if (min != null && max != null) {
                return cb.between(root.get("amount"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("amount"), min);
            } else {
                return cb.lessThanOrEqualTo(root.get("amount"), max);
            }
        };
    }
}
