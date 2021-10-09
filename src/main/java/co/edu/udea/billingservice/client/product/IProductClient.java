package co.edu.udea.billingservice.client.product;

import co.edu.udea.billingservice.model.externalentity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
@RequestMapping(value = "/api/product")
public interface IProductClient {

    @GetMapping(value = "/{id}")
    ResponseEntity<ProductEntity> findProductById(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/stock")
    ResponseEntity<ProductEntity> updateStock(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double stock);
}
