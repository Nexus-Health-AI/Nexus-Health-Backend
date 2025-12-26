package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.InvoiceRequest;
import com.uciap.medicrea.dto.InvoiceResponse;
import com.uciap.medicrea.dto.PaymentRequest;
import com.uciap.medicrea.dto.PaymentResponse;
import com.uciap.medicrea.service.InvoiceService;
import com.uciap.medicrea.service.PaymentService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    public BillingController(InvoiceService invoiceService, PaymentService paymentService) {
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    @PostMapping("/invoices")
    public InvoiceResponse createInvoice(@RequestBody InvoiceRequest request) {
        return invoiceService.createInvoice(request);
    }

    @GetMapping("/invoices")
    public List<InvoiceResponse> getInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/invoices/patient/{patientId}")
    public List<InvoiceResponse> getInvoicesForPatient(@PathVariable String patientId) {
        return invoiceService.getInvoicesForPatient(patientId);
    }

    @PostMapping("/payments")
    public PaymentResponse recordPayment(@RequestBody PaymentRequest request) {
        return paymentService.recordPayment(request);
    }

    @GetMapping("/payments/invoice/{invoiceId}")
    public List<PaymentResponse> getPaymentsForInvoice(@PathVariable String invoiceId) {
        return paymentService.getPaymentsForInvoice(invoiceId);
    }
}
