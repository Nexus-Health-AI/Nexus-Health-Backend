package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.PaymentRequest;
import com.uciap.medicrea.dto.PaymentResponse;
import com.uciap.medicrea.model.Invoice;
import com.uciap.medicrea.model.Payment;
import com.uciap.medicrea.repository.InvoiceRepository;
import com.uciap.medicrea.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;

    public PaymentService(PaymentRepository paymentRepository,
                          InvoiceRepository invoiceRepository,
                          InvoiceService invoiceService) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
    }

    public PaymentResponse recordPayment(PaymentRequest request) {
        Invoice invoice = invoiceService.getInvoiceEntity(request.getInvoiceId());
        Payment payment = new Payment();
        payment.setInvoiceId(request.getInvoiceId());
        payment.setAmount(request.getAmount());
        payment.setPaidAt(request.getPaidAt() != null ? request.getPaidAt() : LocalDateTime.now());
        payment.setMethod(request.getMethod());
        payment.setReference(request.getReference());
        payment.setStatus(request.getStatus() == null || request.getStatus().isBlank() ? "RECEIVED" : request.getStatus());

        Payment saved = paymentRepository.save(payment);
        updateInvoiceStatus(invoice.getId());
        return toResponse(saved);
    }

    public List<PaymentResponse> getPaymentsForInvoice(String invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private void updateInvoiceStatus(String invoiceId) {
        invoiceService.refreshInvoiceTotal(invoiceId);
        Invoice invoice = invoiceService.getInvoiceEntity(invoiceId);
        BigDecimal total = invoice.getTotalAmount() == null ? BigDecimal.ZERO : invoice.getTotalAmount();
        BigDecimal paid = paymentRepository.findByInvoiceId(invoiceId).stream()
            .map(Payment::getAmount)
            .filter(amount -> amount != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (paid.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus("ISSUED");
        } else if (paid.compareTo(total) >= 0 && total.compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus("PAID");
        } else {
            invoice.setStatus("PARTIALLY_PAID");
        }
        invoiceRepository.save(invoice);
    }

    private PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setInvoiceId(payment.getInvoiceId());
        response.setAmount(payment.getAmount());
        response.setPaidAt(payment.getPaidAt());
        response.setMethod(payment.getMethod());
        response.setReference(payment.getReference());
        response.setStatus(payment.getStatus());
        return response;
    }
}
