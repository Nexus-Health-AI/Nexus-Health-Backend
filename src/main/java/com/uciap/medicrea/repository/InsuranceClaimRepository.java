package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.InsuranceClaim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, String> {
    List<InsuranceClaim> findByStatus(String status);
}
