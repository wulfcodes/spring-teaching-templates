package io.wulfcodes.secc.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final String username;
    private String apiKey;

    public ApiKeyAuthenticationToken(String apiKey) {
        super(null);
        this.username = null;
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(String username, String apiKey, Collection<? extends  GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.apiKey = apiKey;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.apiKey;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.apiKey = null;
    }
}
