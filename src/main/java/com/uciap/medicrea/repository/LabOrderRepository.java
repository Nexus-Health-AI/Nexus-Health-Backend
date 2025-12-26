package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.LabOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabOrderRepository extends JpaRepository<LabOrder, String> {
    List<LabOrder> findByPatientId(String patientId);
    List<LabOrder> findByStatus(String status);
}
