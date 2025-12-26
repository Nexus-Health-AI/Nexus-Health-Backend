package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.InvoiceItemRequest;
import com.uciap.medicrea.dto.InvoiceItemResponse;
import com.uciap.medicrea.dto.InvoiceRequest;
import com.uciap.medicrea.dto.InvoiceResponse;
import com.uciap.medicrea.model.Invoice;
import com.uciap.medicrea.model.InvoiceItem;
import com.uciap.medicrea.repository.InvoiceItemRepository;
import com.uciap.medicrea.repository.InvoiceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final PatientService patientService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceItemRepository invoiceItemRepository,
                          PatientService patientService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.patientService = patientService;
    }

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        patientService.getPatientEntity(request.getPatientId());
        Invoice invoice = new Invoice();
        invoice.setPatientId(request.getPatientId());
        invoice.setVisitId(request.getVisitId());
        invoice.setIssuedDate(request.getIssuedDate() != null ? request.getIssuedDate() : LocalDateTime.now());
        invoice.setDueDate(request.getDueDate());
        invoice.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "ISSUED" : request.getStatus());
        invoice.setNotes(request.getNotes());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        List<InvoiceItemRequest> items = request.getItems() != null ? request.getItems() : Collections.emptyList();
        List<InvoiceItem> savedItems = items.stream()
            .map(item -> toItem(savedInvoice.getId(), item))
            .map(invoiceItemRepository::save)
            .collect(Collectors.toList());

        BigDecimal total = savedItems.stream()
            .map(InvoiceItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        savedInvoice.setTotalAmount(total);
        Invoice finalInvoice = invoiceRepository.save(savedInvoice);
        return toResponse(finalInvoice, savedItems);
    }

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
            .map(invoice -> toResponse(invoice, invoiceItemRepository.findByInvoiceId(invoice.getId())))
            .collect(Collectors.toList());
    }

    public List<InvoiceResponse> getInvoicesForPatient(String patientId) {
        patientService.getPatientEntity(patientId);
        return invoiceRepository.findByPatientId(patientId).stream()
            .map(invoice -> toResponse(invoice, invoiceItemRepository.findByInvoiceId(invoice.getId())))
            .collect(Collectors.toList());
    }

    public Invoice getInvoiceEntity(String invoiceId) {
        return invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public InvoiceResponse refreshInvoiceTotal(String invoiceId) {
        Invoice invoice = getInvoiceEntity(invoiceId);
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceId(invoiceId);
        BigDecimal total = items.stream()
            .map(InvoiceItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoice.setTotalAmount(total);
        return toResponse(invoiceRepository.save(invoice), items);
    }

    private InvoiceItem toItem(String invoiceId, InvoiceItemRequest request) {
        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(invoiceId);
        item.setDescription(request.getDescription());
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
        BigDecimal amount = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        item.setAmount(amount);
        return item;
    }

    private InvoiceResponse toResponse(Invoice invoice, List<InvoiceItem> items) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setPatientId(invoice.getPatientId());
        response.setVisitId(invoice.getVisitId());
        response.setIssuedDate(invoice.getIssuedDate());
        response.setDueDate(invoice.getDueDate());
        response.setStatus(invoice.getStatus());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setNotes(invoice.getNotes());
        response.setItems(items.stream().map(this::toItemResponse).collect(Collectors.toList()));
        return response;
    }

    private InvoiceItemResponse toItemResponse(InvoiceItem item) {
        InvoiceItemResponse response = new InvoiceItemResponse();
        response.setId(item.getId());
        response.setDescription(item.getDescription());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setAmount(item.getAmount());
        return response;
    }
}
