package com.dd.campsites.repository;

import com.dd.campsites.domain.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    @Query("select invoice from Invoice invoice where invoice.customer.login = ?#{principal.username}")
    List<Invoice> findByCustomerIsCurrentUser();
}
