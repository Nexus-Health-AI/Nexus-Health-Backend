package com.uciap.medicrea.repository;

import com.uciap.medicrea.dto.MedicalReportResponse;
import com.uciap.medicrea.model.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, String> {
    List<MedicalReport> findByPatientId(String patientId);
}
