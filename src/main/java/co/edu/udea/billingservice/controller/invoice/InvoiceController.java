package co.edu.udea.billingservice.controller.invoice;

import co.edu.udea.billingservice.client.product.IProductClient;
import co.edu.udea.billingservice.model.entity.InvoiceEntity;
import co.edu.udea.billingservice.model.externalentity.ProductEntity;
import co.edu.udea.billingservice.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/billing")
public class InvoiceController {

//    @Autowired
//    private CircuitBreakerFactory cbFactory;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private IProductClient iProductClient;

    @GetMapping(value = "/f/{id}")
    public ResponseEntity<ProductEntity> findProductById(@PathVariable("id") Long id){
        ProductEntity productEntity =  iProductClient.findProductById(id).getBody();
        if (productEntity == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productEntity);
    }

    // -------------------Retrieve All Invoices--------------------------------------------
    @GetMapping
    public ResponseEntity<List<InvoiceEntity>> listAllInvoices() {
        List<InvoiceEntity> invoices = invoiceService.findAllInvoice();
        if (invoices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    // -------------------Retrieve Single Invoice------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<InvoiceEntity> getInvoice(@PathVariable("id") long id) {
        InvoiceEntity invoice = invoiceService.getInvoiceById(id);
        if (null == invoice) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    // -------------------Create a Invoice-------------------------------------------
    @PostMapping
    public ResponseEntity<InvoiceEntity> createInvoice(@RequestBody InvoiceEntity invoice) {
        InvoiceEntity invoiceDB = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDB);
    }

    // ------------------- Update a Invoice ------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable("id") long id, @RequestBody InvoiceEntity invoice) {
        invoice.setId(id);
        InvoiceEntity currentInvoice = invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(currentInvoice);
    }

    // ------------------- Delete a Invoice-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<InvoiceEntity> deleteInvoice(@PathVariable("id") long id) {

        InvoiceEntity invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

}
