package io.wulfcodes.secc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.secc.model.ro.AuthRequest;
import io.wulfcodes.secc.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    // there should an open registration endpoint
    /*
        - in jwt flow credentials are not validated against every request, rather the jwt token is only validated
        so spring's UserDetails and UserDetails are not required, as spring won't be using it for jwt flow

        - credentials are only used for login purpose, which is responsible for generating the token for spring's jwt auth flow
     */

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {
        // username and password is validated against

        String jwtToken = authService.generateToken(authRequest.username());

        ResponseCookie cookie = ResponseCookie.from("accessToken", jwtToken)
                                              .httpOnly(true)       // Blocks JavaScript from reading it (XSS protection)
                                              .secure(true)         // Only send over HTTPS
                                              .path("/")            // Available for entire app
                                              .maxAge(3600)         // 1 Hour (Matches your JWT expiry)
                                              .sameSite("Strict")   // Protects against CSRF
                                              .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("User logged in successfully");
    }
}
