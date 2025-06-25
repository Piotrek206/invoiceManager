package com.demo.invoice.service;

import com.demo.invoice.model.Invoice;
import com.demo.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Page<Invoice> getAllInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getInvoiceDate() == null) {
            invoice.setInvoiceDate(LocalDate.now());
        }
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> updateInvoice(Long id, Invoice invoice) {
        if (!invoiceRepository.existsById(id)) {
            return Optional.empty();
        }
        invoice.setId(id);
        return Optional.of(invoiceRepository.save(invoice));
    }

    public Optional<Invoice> partialUpdateInvoice(Long id, Map<String, Object> updates) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    if (updates.containsKey("invoiceNumber")) {
                        invoice.setInvoiceNumber((String) updates.get("invoiceNumber"));
                    }
                    if (updates.containsKey("totalAmount")) {
                        invoice.setTotalAmount(BigDecimal.valueOf(((Number) updates.get("totalAmount")).doubleValue()));
                    }
                    if (updates.containsKey("invoiceDate")) {
                        invoice.setInvoiceDate(LocalDate.parse((String) updates.get("invoiceDate")));
                    }
                    return invoiceRepository.save(invoice);
                });
    }

    public boolean deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            return false;
        }
        invoiceRepository.deleteById(id);
        return true;
    }

    public Page<Invoice> findByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return invoiceRepository.findByInvoiceDateBetween(startDate, endDate, pageable);
    }

    public Page<Invoice> findByMinAmount(BigDecimal minAmount, Pageable pageable) {
        return invoiceRepository.findByTotalAmountGreaterThanEqual(minAmount, pageable);
    }
}
