package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.EmergencyCase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyCaseRepository extends JpaRepository<EmergencyCase, String> {
    List<EmergencyCase> findByStatus(String status);
}
