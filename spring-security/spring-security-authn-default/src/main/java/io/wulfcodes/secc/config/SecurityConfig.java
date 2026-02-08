package io.wulfcodes.secc.config;

import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    /**
     * SecurityFilterChain used -> org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.SecurityFilterChainConfiguration
     * - It enables FormLogin (using FormLoginConfigurer) and Basic Auth (using HttpBasicConfigurer) by default and authenticates all the requests
     * -
     * - Uses DaoAuthenticationProvider by default for validation
     * - Uses InMemoryUserDetailsManager with a single user with username "user" and a randomly generated password by default for UserDetailsService
     * <p>
     * - Creating custom bean of any component, such SecurityFilter, AuthenticationProvider, UserDetailsService, PasswordEncoder
     * will override the default configuration of those classes
     */

    @Bean
    public AuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService) {
        return new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                System.out.println("Custom Authentication Provider");
                String username = (String) authentication.getPrincipal();
                String password = (String) authentication.getCredentials();

                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Will throw UserNotFoundException -> AuthenticationException when user not found
                if (Objects.isNull(password) || !password.equals(userDetails.getPassword()))
                    throw new BadCredentialsException("Password in invalid");
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public UserDetailsService customUserDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("Swayam")
                                .authorities("ROLE_ADMIN", "ROLE_MODERATOR")
                                .password("sid123")
                                .passwordEncoder(passwordEncoder::encode)
                                .build();

        UserDetails user2 = User.withUsername("DJ")
                                .password("dj123")
                                .authorities("ROLE_USER")
                                .passwordEncoder(passwordEncoder::encode)
                                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
