package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Prescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    List<Prescription> findByPatientId(String patientId);
}
