package io.wulfcodes.api.controller.rest.v1;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.api.model.Product;

@RestController
@RequestMapping(
    path = "/v1/products",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductController {
    private static final List<Product> PRODUCTS = new ArrayList<>();


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "type", required = false) String type) {
        if (PRODUCTS.isEmpty())
            return ResponseEntity.notFound().build();
        else if (Objects.nonNull(type))
            return ResponseEntity.ok(PRODUCTS.stream().filter(product -> product.getType().equals(type)).toList());
        return ResponseEntity.ok().body(PRODUCTS);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId) {
        for (Product product : PRODUCTS) {
            if (Objects.equals(product.getId(), productId))
                return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        boolean productPresent = PRODUCTS.stream().anyMatch(p -> p.getName().equals(product.getName()));
        if (productPresent)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        URI location = URI.create("/v1/products/" + product.getId());
        return ResponseEntity.created(location).body(product);
    }

}
