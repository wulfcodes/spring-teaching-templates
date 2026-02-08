package io.wulfcodes.secc.security.provider;

import io.wulfcodes.secc.model.User;
import io.wulfcodes.secc.security.token.ApiKeyAuthenticationToken;
import io.wulfcodes.secc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String)authentication.getCredentials();
        User user = ((UserService)userDetailsService).loadUserByApiKey(apiKey);

        return new ApiKeyAuthenticationToken(user.getUsername(), user.getApiKey(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
