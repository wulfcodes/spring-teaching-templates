package io.wulfcodes.secc.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User is authenticated");
    }

}
