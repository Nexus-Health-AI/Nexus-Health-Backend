package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.MedicineBatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineBatchRepository extends JpaRepository<MedicineBatch, String> {
    List<MedicineBatch> findByMedicineId(String medicineId);
}
