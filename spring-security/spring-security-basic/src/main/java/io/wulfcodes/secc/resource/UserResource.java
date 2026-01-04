package io.wulfcodes.secc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userDetailsManager.userExists(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userDetailsManager.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    // 🔐 Change Password (Protected)
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        // Get the currently logged-in user
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        // Verify the old password matches what we have in DB
        // (In a real app, you might want deeper validation here)
        
        // Change the password
        // The manager handles the hashing and DB update automatically if configured correctly,
        // but often 'changePassword' expects the old password to be validated by the manager context.
        // A safer direct approach using the manager:
        
        String newEncodedPassword = passwordEncoder.encode(request.newPassword());
        
        // This method updates the password for the current authentication context
        userDetailsManager.changePassword(request.oldPassword(), newEncodedPassword);

        return ResponseEntity.ok("Password updated successfully");
    }

    // 🔐 Check if User Exists (Protected - e.g., for Admin checks)
    @GetMapping("/exists/{username}")
    public ResponseEntity<Boolean> userExists(@PathVariable String username) {
        boolean exists = userDetailsManager.userExists(username);
        return ResponseEntity.ok(exists);
    }
    
    // DTO for password change
    public record ChangePasswordRequest(String oldPassword, String newPassword) {}
}