package io.wulfcodes.secc.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestResource {

    @GetMapping("/user")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("User is authenticated and has READ access");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("Admin is authenticated and has READ / WRITE access");
    }
}
