package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Admission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRepository extends JpaRepository<Admission, String> {
    List<Admission> findByPatientId(String patientId);
    List<Admission> findByStatus(String status);
}
