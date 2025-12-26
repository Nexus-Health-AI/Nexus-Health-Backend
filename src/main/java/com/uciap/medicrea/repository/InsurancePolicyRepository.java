package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.InsurancePolicy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, String> {
    List<InsurancePolicy> findByPatientId(String patientId);
}
