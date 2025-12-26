package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<Nurse, String> {
}
