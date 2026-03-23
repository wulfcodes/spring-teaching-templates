package io.wulfcodes.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.rest.model.Product;


@RestController
public class OutputController {

    @GetMapping("/products/direct")
    public Product fetchProduct() {
        Product product = new Product();
        product.setProductName("T-Shirt");
        product.setPrice(29);
        product.setCategory("Shirt");
        product.setInStock(true);
        product.setDiscount(5);
        return product;
    }

    @GetMapping("/products/wrapper")
    public ResponseEntity<Product> fetchProductWrapper() {
        Product product = new Product();
        product.setProductName("T-Shirt");
        product.setPrice(29);
        product.setCategory("Shirt");
        product.setInStock(true);
        product.setDiscount(5);

        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK)
                                                      .header("X-Custom-Header", "some value")
                                                      .body(product);

        return responseEntity;
    }

    @GetMapping("/products/variations")
    public ResponseEntity<Product> fetchProductVariation() {
        Product product = new Product();
        product.setProductName("T-Shirt");
        product.setPrice(29);
        product.setCategory("Shirt");
        product.setInStock(true);
        product.setDiscount(5);

        // not available for all status codes
        return ResponseEntity.ok(product);
    }

}
