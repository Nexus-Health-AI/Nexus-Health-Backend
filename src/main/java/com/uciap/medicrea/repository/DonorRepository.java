package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, String> {
}
