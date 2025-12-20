package io.wulfcodes.secc.security.provider;

import io.wulfcodes.secc.security.token.ApiKeyAuthenticationToken;
import io.wulfcodes.secc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String)authentication.getCredentials();
        String username = userService.validateUser(apiKey);

        return new ApiKeyAuthenticationToken(username, apiKey, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
