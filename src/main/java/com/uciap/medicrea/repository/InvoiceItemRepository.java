package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.InvoiceItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, String> {
    List<InvoiceItem> findByInvoiceId(String invoiceId);
}
