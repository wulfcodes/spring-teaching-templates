package io.wulfcodes.secc.service;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static final Map<String, String> USERS = new HashMap<>(Map.of(
            "apikey1", "user1",
            "apikey2", "user2",
            "apikey3", "user3"
    ));

    public String validateUser(String apiKey) throws AuthenticationException {
        String username = USERS.get(apiKey);
        if (username == null) {
            throw new BadCredentialsException("User not found with API key: " + apiKey);
        }
        return username;
    }

}
