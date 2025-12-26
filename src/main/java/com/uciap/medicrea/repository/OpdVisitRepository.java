package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.OpdVisit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpdVisitRepository extends JpaRepository<OpdVisit, String> {
    List<OpdVisit> findByPatientId(String patientId);
    List<OpdVisit> findByStatus(String status);
}
