package co.edu.udea.billingservice.repository;

import co.edu.udea.billingservice.model.entity.InvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItemEntity, Long> {
}
