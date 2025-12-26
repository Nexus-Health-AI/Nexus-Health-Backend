package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.BloodUnit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, String> {
    List<BloodUnit> findByStatus(String status);
    List<BloodUnit> findByBloodGroup(String bloodGroup);
}
