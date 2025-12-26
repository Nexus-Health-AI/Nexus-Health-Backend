package com.uciap.medicrea.dto;

import java.math.BigDecimal;

public class InvoiceItemRequest {

    private String description;
    private int quantity;
    private BigDecimal unitPrice;

    public InvoiceItemRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
