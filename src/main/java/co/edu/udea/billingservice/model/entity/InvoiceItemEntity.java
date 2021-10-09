package co.edu.udea.billingservice.model.entity;

import co.edu.udea.billingservice.model.externalentity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor

@Entity
@Table(name = "tbl_invoice_items")
public class InvoiceItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Positive(message = "El stock debe ser mayor que cero")
    private Double quantity;

    @Column(name = "price")
    private Double  price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private ProductEntity product;

    @Transient
    private Double subTotal;

    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }

    public InvoiceItemEntity(){
        this.quantity=(double) 0;
        this.price=(double) 0;

    }

}
