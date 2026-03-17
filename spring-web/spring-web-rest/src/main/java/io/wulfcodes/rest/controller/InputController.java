package io.wulfcodes.rest.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.wulfcodes.rest.model.ContactForm;
import io.wulfcodes.rest.model.Product;

@RestController
public class InputController {

    /* Types of Request Mapping */

    @GetMapping("/get-route")
    public void getRoute() {
        System.out.println("GET or HEAD route called");
    }

    @PostMapping("/post-route")
    public void postRoute() {
        System.out.println("POST route called");
    }

    @PutMapping("/put-route")
    public void putRoute() {
        System.out.println("PUT route called");
    }

    @PatchMapping("/patch-route")
    public void patchRoute() {
        System.out.println("PATCH route called");
    }

    @DeleteMapping("/delete-route")
    public void deleteRoute() {
        System.out.println("DELETE route called");
    }

    /* Request Parameters */

    @GetMapping("/products")
    public void showQuery(@RequestParam("product-type") String productType, @RequestParam("product-price") Float productPrice) {
        System.out.println("%s %s".formatted(productType, productPrice));
    }

    @GetMapping("/products/{productType}")
    public void showPath(@PathVariable("productType") String productType) {
        System.out.println(productType);
    }

    @GetMapping("/products/{productType}/suggestions/{accessoryType}")
    public void showMatrix(
        @PathVariable("productType") String productType, @MatrixVariable(pathVar = "productType", name = "brand") String productBrand,
        @PathVariable("accessoryType") String accessoryType, @MatrixVariable(pathVar = "accessoryType", name = "color") String accessoryColor
    ) {
        System.out.println(productType + " " + productBrand + " " + accessoryType + " " + accessoryColor);
    }

    /* Capturing Request Body */

    @PostMapping("/products")
    public void showBody(@RequestBody Product product) {
        System.out.println(product);
    }

    @PostMapping(path = "/products-json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void showBodyJson(@RequestBody Product product) {
        System.out.println(product);
    }

    @PostMapping(value = "/upload-stream", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void uploadAsStream(InputStream inputStream) throws Exception {
        Path projectRoot = Paths.get("").toAbsolutePath();
        Path uploadsDir = projectRoot.resolve("src/main/resources/uploads");
        Files.createDirectories(uploadsDir);
        String baseName = "upload_" + UUID.randomUUID();
        Path tempFilePath = uploadsDir.resolve(baseName + ".bin");
        Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        byte[] header = new byte[8];
        try (InputStream fileStream = Files.newInputStream(tempFilePath)) {
            fileStream.read(header);
        }

        String extension = ".bin";
        if (isJPEG(header)) {
            extension = ".jpg";
        } else if (isPNG(header)) {
            extension = ".png";
        }

        // 5. Rename the file if we detected a known format
        if (!extension.equals(".bin")) {
            Path finalFilePath = uploadsDir.resolve(baseName + extension);
            Files.move(tempFilePath, finalFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Detected and renamed to: " + finalFilePath);
        } else {
            System.out.println("Saved as default binary: " + tempFilePath);
        }
    }

    // --- Helper methods to check Magic Bytes ---

    private boolean isJPEG(byte[] header) {
        if (header.length < 3) return false;
        // JPEGs always start with Hex: FF D8 FF
        return header[0] == (byte) 0xFF &&
            header[1] == (byte) 0xD8 &&
            header[2] == (byte) 0xFF;
    }

    private boolean isPNG(byte[] header) {
        if (header.length < 4) return false;
        // PNGs always start with Hex: 89 50 4E 47 (which spells ‰PNG)
        return header[0] == (byte) 0x89 &&
            header[1] == 0x50 &&
            header[2] == 0x4E &&
            header[3] == 0x47;
    }

}
