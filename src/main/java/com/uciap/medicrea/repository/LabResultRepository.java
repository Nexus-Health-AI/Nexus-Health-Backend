package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.LabResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabResultRepository extends JpaRepository<LabResult, String> {
    List<LabResult> findByPatientId(String patientId);
    List<LabResult> findByOrderId(String orderId);
}
