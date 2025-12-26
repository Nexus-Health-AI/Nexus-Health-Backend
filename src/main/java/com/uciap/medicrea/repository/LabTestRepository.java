package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.LabTest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabTestRepository extends JpaRepository<LabTest, String> {
    Optional<LabTest> findByTestName(String testName);
}
