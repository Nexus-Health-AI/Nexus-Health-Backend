package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Ward;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDepartment(String department);
}
