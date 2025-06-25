package com.demo.invoice.repository;

import com.demo.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Invoice> findByTotalAmountGreaterThanEqual(BigDecimal amount, Pageable pageable);
    boolean existsByInvoiceNumber(String invoiceNumber);
}
