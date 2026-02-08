package io.wulfcodes.secc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import jakarta.transaction.Transactional;
import io.wulfcodes.secc.model.User;
import io.wulfcodes.secc.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private static final SecureRandom secureRandom = new SecureRandom(); // Thread-safe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // URL-safe (uses - and _)

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User saveUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setApiKey(generateApiKey());

        return userRepository.save(user);
    }

    private static String generateApiKey() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.withoutPadding().encodeToString(randomBytes);
    }

    public User loadUserByApiKey(String apiKey) throws AuthenticationException {
        return userRepository.findByApiKey(apiKey)
                             .orElseThrow(() -> new BadCredentialsException("User not found with API key: " + apiKey));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
