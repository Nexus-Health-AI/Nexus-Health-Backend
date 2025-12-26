package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Medicine;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    @Query("select m from Medicine m where m.reorderLevel is not null and m.stock <= m.reorderLevel")
    List<Medicine> findLowStock();
}
