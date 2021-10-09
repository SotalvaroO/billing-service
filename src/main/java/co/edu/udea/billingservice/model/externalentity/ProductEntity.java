package co.edu.udea.billingservice.model.externalentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductEntity {

    private Long id;
    private String code;
    private String name;
    private String description;
    private CategoryEntity category;
    private Double price;
    private int stock;
    private String imgReference;
    private String status;

}
