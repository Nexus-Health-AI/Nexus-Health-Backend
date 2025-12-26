package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
