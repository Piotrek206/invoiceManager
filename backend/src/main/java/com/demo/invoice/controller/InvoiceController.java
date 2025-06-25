package com.demo.invoice.controller;

import com.demo.invoice.exception.InvoiceNotFoundException;
import com.demo.invoice.model.Invoice;
import com.demo.invoice.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        log.info("InvoiceController initialized");
    }

    @GetMapping
        public ResponseEntity<Page<Invoice>> getAllInvoices(Pageable pageable) {
        log.info("Getting all invoices with pagination: {}", pageable);
        return ResponseEntity.ok(invoiceService.getAllInvoices(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        log.info("Getting invoice by id: {}", id);
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> {
                    log.error("Invoice not found with id: {}", id);
                    return new InvoiceNotFoundException(id);
                });
        log.debug("Retrieved invoice: {}", invoice);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        log.info("Creating new invoice");
        // Ensure a new invoice doesn't have an ID set
        if (invoice.getId() != null) {
            log.warn("Attempted to create invoice with predefined ID: {}", invoice.getId());
            return ResponseEntity.badRequest().build();
        }

        Invoice savedInvoice = invoiceService.createInvoice(invoice);
        log.info("Invoice created with id: {}", savedInvoice.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(
            @PathVariable Long id,
            @RequestBody Invoice invoice) {
        log.info("Updating invoice with id: {}", id);
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice)
                .orElseThrow(() -> {
                    log.error("Failed to update - invoice not found with id: {}", id);
                    return new InvoiceNotFoundException(id);
                });
        log.info("Invoice updated successfully: {}", id);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.info("Deleting invoice with id: {}", id);
        if (!invoiceService.deleteInvoice(id)) {
            log.error("Failed to delete - invoice not found with id: {}", id);
            throw new InvoiceNotFoundException(id);
        }
        log.info("Invoice deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Invoice> partialUpdateInvoice(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        log.info("Partially updating invoice with id: {} and fields: {}", id, updates.keySet());
        Invoice updatedInvoice = invoiceService.partialUpdateInvoice(id, updates)
                .orElseThrow(() -> {
                    log.error("Failed to patch - invoice not found with id: {}", id);
                    return new InvoiceNotFoundException(id);
                });
        log.info("Invoice patched successfully: {}", id);
        return ResponseEntity.ok(updatedInvoice);
    }

    @GetMapping("/search/by-date-range")
    public ResponseEntity<Page<Invoice>> getInvoicesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Pageable pageable) {
        log.info("Searching invoices by date range: {} to {}", startDate, endDate);
        return ResponseEntity.ok(invoiceService.findByDateRange(startDate, endDate, pageable));
    }

    @GetMapping("/search/by-min-amount")
    public ResponseEntity<Page<Invoice>> getInvoicesByMinAmount(
            @RequestParam BigDecimal minAmount,
            Pageable pageable) {
        log.info("Searching invoices by minimum amount: {}", minAmount);
        return ResponseEntity.ok(invoiceService.findByMinAmount(minAmount, pageable));
    }
}
