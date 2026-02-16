package io.wulfcodes.secc.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken(String username) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("self")       // iss
                                          .audience(Collections.singletonList("self"))  // aud
                                          .subject(username) // sub
                                          .issuedAt(now)    // iat
                                          .expiresAt(now.plus(1, ChronoUnit.HOURS)) // exp
                                          .id(UUID.randomUUID().toString()) // jti
                                          .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }

}
