package co.edu.udea.billingservice.repository;

import co.edu.udea.billingservice.model.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);

}
