package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Bed;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed, String> {
    List<Bed> findByWardId(String wardId);
    List<Bed> findByStatus(String status);
}
