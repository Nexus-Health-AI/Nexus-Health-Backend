package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
