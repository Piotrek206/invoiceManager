package com.demo.invoice.exception;

public class InvoiceNotFoundException extends RuntimeException {
    
    public InvoiceNotFoundException(Long id) {
        super(String.format("Invoice with id %d not found", id));
    }
    
    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
