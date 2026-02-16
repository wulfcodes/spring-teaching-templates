package io.wulfcodes.secc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.secc.model.ro.AuthRequest;
import io.wulfcodes.secc.service.AuthService;
import io.wulfcodes.secc.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private static final String ADMIN_KEY = "my-secret-admin-key";

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest request) {
        if (userService.userExists(request.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        userService.createUser(request.username(), request.password(), ADMIN_KEY.equals(request.adminKey()));
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {
        if (!userService.userExists(authRequest.username(), authRequest.password()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");

        boolean isAdmin = ADMIN_KEY.equals(authRequest.adminKey());
        String jwtToken = authService.generateToken(authRequest.username(), isAdmin);

        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                                              .httpOnly(true)       // Blocks JavaScript from reading it (XSS protection)
                                              .secure(true)         // Only send over HTTPS
                                              .path("/")            // Available for entire app
                                              .maxAge(3600)         // 1 Hour (Matches your JWT expiry)
                                              .sameSite("Strict")   // Protects against CSRF
                                              .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("User logged in successfully");
    }
}
