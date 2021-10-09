package co.edu.udea.billingservice.service.invoice;

import co.edu.udea.billingservice.model.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {

    List<InvoiceEntity> findAllInvoice();
    InvoiceEntity getInvoiceById(Long id);
    InvoiceEntity getInvoiceByNumber(String number);
    InvoiceEntity createInvoice(InvoiceEntity invoiceEntity);
    InvoiceEntity updateInvoice(InvoiceEntity invoiceEntity);
    InvoiceEntity deleteInvoice(InvoiceEntity invoiceEntity);

}
