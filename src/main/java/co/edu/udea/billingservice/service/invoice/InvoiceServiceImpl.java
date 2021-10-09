package co.edu.udea.billingservice.service.invoice;

import co.edu.udea.billingservice.client.product.IProductClient;
import co.edu.udea.billingservice.model.entity.InvoiceEntity;
import co.edu.udea.billingservice.model.entity.InvoiceItemEntity;
import co.edu.udea.billingservice.model.externalentity.ProductEntity;
import co.edu.udea.billingservice.repository.InvoiceItemRepository;
import co.edu.udea.billingservice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;
    @Autowired
    private IProductClient iProductClient;

    @Override
    public List<InvoiceEntity> findAllInvoice() {
        List<InvoiceEntity> invoices= invoiceRepository.findAll();
        if (!invoices.isEmpty()){
            for (InvoiceEntity i: invoices
                 ) {
                i.getInvoiceItems().forEach(invoiceItemEntity -> {
                    invoiceItemEntity.setProduct(iProductClient.findProductById(invoiceItemEntity.getProductId()).getBody());
                });
            }
        }
        return invoices;
    }

    @Override
    public InvoiceEntity getInvoiceById(Long id) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(id).orElse(null);
        if (invoiceEntity != null) {
            List<InvoiceItemEntity> invoiceItems = invoiceEntity.getInvoiceItems().stream().map(invoiceItemEntity -> {
                ProductEntity productEntity = iProductClient.findProductById(invoiceItemEntity.getId()).getBody();
                invoiceItemEntity.setProduct(productEntity);
                return invoiceItemEntity;
            }).collect(Collectors.toList());
            invoiceEntity.setInvoiceItems(invoiceItems);
        }
        return invoiceEntity;
    }

    @Override
    public InvoiceEntity getInvoiceByNumber(String number) {
        return invoiceRepository.findByInvoiceNumber(number).orElse(null);
    }

    @Override
    public InvoiceEntity createInvoice(InvoiceEntity invoiceEntity) {
        InvoiceEntity invoiceEntityDB = getInvoiceByNumber(invoiceEntity.getInvoiceNumber());
        if (invoiceEntityDB != null) {
            invoiceEntityDB.getInvoiceItems().forEach(invoiceItemEntity -> {
                invoiceItemEntity.setProduct(iProductClient.findProductById(invoiceItemEntity.getProductId()).getBody());
            });
            return invoiceEntityDB;
        }
        invoiceEntity.setStatus("CREATED");
        invoiceEntityDB = invoiceRepository.save(invoiceEntity);
        invoiceEntityDB.getInvoiceItems().forEach(invoiceItemEntity -> {
            iProductClient.updateStock(invoiceItemEntity.getProductId() , invoiceItemEntity.getQuantity() * -1);
            invoiceItemEntity.setProduct(iProductClient.findProductById(invoiceItemEntity.getProductId()).getBody());
        });
        return invoiceEntityDB;
    }

    @Override
    public InvoiceEntity updateInvoice(InvoiceEntity invoiceEntity) {
        InvoiceEntity invoiceEntityDB = getInvoiceById(invoiceEntity.getId());
        if (invoiceEntityDB == null) {
            return null;
        }
        invoiceEntityDB.setInvoiceNumber(invoiceEntity.getInvoiceNumber());
        invoiceEntityDB.setDescription(invoiceEntity.getDescription());
        invoiceEntityDB.setCustomerId(invoiceEntity.getCustomerId());
        invoiceEntityDB.getInvoiceItems().clear();
        invoiceEntityDB.setInvoiceItems(invoiceEntity.getInvoiceItems());
        return invoiceRepository.save(invoiceEntityDB);
    }

    @Override
    public InvoiceEntity deleteInvoice(InvoiceEntity invoiceEntity) {
        InvoiceEntity invoiceEntityDB = getInvoiceById(invoiceEntity.getId());
        if (invoiceEntityDB == null) {
            return null;
        }
        invoiceEntityDB.setStatus("DELETED");
        return invoiceRepository.save(invoiceEntityDB);
    }

}
