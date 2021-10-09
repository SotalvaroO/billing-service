package co.edu.udea.billingservice.model.externalentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryEntity {

    private Long id;
    private String code;
    private String name;
    private String status;
    private Date createAt;

}
