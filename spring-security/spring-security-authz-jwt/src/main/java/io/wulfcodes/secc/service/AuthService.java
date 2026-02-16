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

    public String generateToken(String username, boolean isAdmin) {
        Instant now = Instant.now();

        String scopes = "READ";
        if (isAdmin)
            scopes = scopes.concat(" WRITE");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("self")
                                          .audience(Collections.singletonList("self"))
                                          .subject(username)
                                          .issuedAt(now)
                                          .expiresAt(now.plus(1, ChronoUnit.HOURS))
                                          .id(UUID.randomUUID().toString())
                                          .claim("scp", scopes) // space separated "READ WRITE"
                                          .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }

}
