package io.wulfcodes.secc.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.secc.model.User;
import io.wulfcodes.secc.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping("/{username}")
    private ResponseEntity<?> createUser(@PathVariable String username) {
        User user = userService.saveUser(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
