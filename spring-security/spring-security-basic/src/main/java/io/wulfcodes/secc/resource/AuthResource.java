package io.wulfcodes.secc.resource;

import java.util.Collections;
import io.wulfcodes.secc.model.ro.AuthRequest;
import io.wulfcodes.secc.model.ro.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest request) {
        if (userDetailsManager.userExists(request.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.username(), hashedPassword, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        userDetailsManager.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();
        UserDetails user = userDetailsManager.loadUserByUsername(username);

        String oldHashedPassword = passwordEncoder.encode(request.oldPassword());
        if (!oldHashedPassword.equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Old Password does not match");
        }
        
        String newHashedPassword = passwordEncoder.encode(request.newPassword());
        userDetailsManager.changePassword(request.oldPassword(), newHashedPassword);

        return ResponseEntity.ok("Password updated successfully");
    }

}