package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByPatientId(String patientId);
}
