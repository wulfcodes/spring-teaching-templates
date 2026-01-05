package io.wulfcodes.api.controller.rest.v1;

import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import io.wulfcodes.api.model.Product;

@RestController // Combines @Controller and @ResponseBody
@RequestMapping("/api/v1/products") // Base path for all endpoints in this class
public class ProductController {

    // ----------------------------------------------------------------
    // 1. FETCH BY ID (Path Variable + ETag + Content Negotiation)
    // ----------------------------------------------------------------
    @GetMapping(
        value = "/{id}", 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Product> getProductById(
            @PathVariable("id") Long productId,
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch,
            WebRequest webRequest
    ) {
        // Simulate fetching data
        Product product = new Product(productId, "Super Gadget", 100.0);
        
        // ETag Logic: Calculate hash of current content
        String currentEtag = "\"" + product.hashCode() + "\""; // Simple hash example

        // Check if the client already has this version. 
        // If true, Spring immediately sends 304 Not Modified and returns null.
        if (webRequest.checkNotModified(currentEtag)) {
            return null; 
        }

        return ResponseEntity.ok()
                .eTag(currentEtag) // Send the ETag to the client for future caching
                .lastModified(Instant.now())
                .body(product);
    }

    // ----------------------------------------------------------------
    // 2. SEARCH (Query Params + Default Values + Header Access)
    // ----------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sort", defaultValue = "desc") String sortDirection,
            @RequestHeader("User-Agent") String userAgent
    ) {
        System.out.println("Searching for: " + query + " | Page: " + page + " | Client: " + userAgent);
        
        // Simulate result
        return ResponseEntity.ok(List.of(new Product(1L, "Item A", 50.0)));
    }

    // ----------------------------------------------------------------
    // 3. CREATE (Request Body + Validation + Location Header)
    // ----------------------------------------------------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        // Simulate saving
        newProduct.setId(999L); // ID assigned by DB

        // Best Practice: Return 201 Created with the Location header pointing to the new resource
        URI location = URI.create("/api/v1/products/" + newProduct.getId());

        return ResponseEntity.created(location)
                .body(newProduct);
    }

    // ----------------------------------------------------------------
    // 4. MATRIX VARIABLES (Complex Hierarchical Filtering)
    // URL Example: /api/v1/products/matrix/electronics;brand=sony;price=low
    // ----------------------------------------------------------------
    @GetMapping("/matrix/{category}")
    public ResponseEntity<String> getByMatrix(
            @PathVariable String category,
            @MatrixVariable(pathVar = "category") Map<String, String> matrixVars
    ) {
        // matrixVars will contain: {brand=sony, price=low}
        return ResponseEntity.ok("Category: " + category + ", Filters: " + matrixVars.toString());
    }

    // ----------------------------------------------------------------
    // 5. UPDATE (Put vs Patch + Request Attribute/Interceptors)
    // ----------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntireProduct(
            @PathVariable Long id, 
            @RequestBody Product product
    ) {
        // PUT implies replacing the entire resource
        return ResponseEntity.noContent().build(); // 204 No Content is common for updates
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePartialProduct(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> updates
    ) {
        // PATCH implies partial update
        return ResponseEntity.ok("Updated fields: " + updates.keySet());
    }

    // ----------------------------------------------------------------
    // 6. ASYNC REQUEST PROCESSING (CompletableFuture)
    // Useful for long-running IO tasks without blocking the Servlet thread
    // ----------------------------------------------------------------
    @GetMapping("/{id}/stats")
    public ResponseEntity<String> getProductStatsAsync(@PathVariable Long id) {
        return ResponseEntity.accepted().body("Async Stats for Product will be Emailed");
    }

    // ----------------------------------------------------------------
    // 7. FILE UPLOAD (OctetStream)
    // ----------------------------------------------------------------
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestPart("file") org.springframework.web.multipart.MultipartFile file
    ) {
        return ResponseEntity.ok("Uploaded: " + file.getOriginalFilename() + " (" + file.getSize() + " bytes)");
    }


    @PostMapping(value = "/upload-stream", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> uploadStream(@RequestBody InputStream inputStream) {
        // 1. Create a buffer (e.g., 8KB)
        byte[] buffer = new byte[8192];
        int bytesRead;

        // 2. Read from network and write to file/process immediately
        try (FileOutputStream outputStream = new FileOutputStream("uploaded_file.dat")) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("File processed efficiently via streaming");
    }

    @PostMapping(value = "/upload-resource", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> uploadResource(@RequestBody Resource resource) {
        try {
            // You can get the InputStream from the resource whenever you are ready
            InputStream is = resource.getInputStream();

            // Example: Pass it directly to a library like AWS S3 SDK
            // s3Client.putObject("bucket-name", "key", is, metadata);

            return ResponseEntity.ok("Received resource of size: " + resource.contentLength());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading resource");
        }
    }

    // ----------------------------------------------------------------
    // 8. EXCEPTION HANDLING (Local to this Controller)
    // ----------------------------------------------------------------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadInput(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
    }

}