package io.wulfcodes.secc.filter;

import io.wulfcodes.secc.security.token.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {
    private boolean ignoreFailure;

    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationConverter authenticationConverter = request -> {
        String apiKey = request.getHeader("X-Api-Key");
        return StringUtils.hasLength(apiKey) ? new ApiKeyAuthenticationToken(apiKey) : null;
    };

    public ApiKeyAuthenticationFilter(@NonNull AuthenticationManager authenticationManager) {
        System.out.println("Initializing ApiKeyAuthenticationFilter with ignoreFailure TRUE");
        this.authenticationManager = authenticationManager;
        this.ignoreFailure = true;
    }

    public ApiKeyAuthenticationFilter(@NonNull AuthenticationManager authenticationManager, @NonNull AuthenticationEntryPoint authenticationEntryPoint) {
        System.out.println("Initializing ApiKeyAuthenticationFilter with ignoreFailure FALSE");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.ignoreFailure = false;
    }

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Authentication authRequest = this.authenticationConverter.convert(request);
            System.out.println("Authenticating with ApiKey");
            if (authRequest == null) {
                filterChain.doFilter(request, response);
                return;
            }
            System.out.println("Authentication request successfully generated");

            Authentication authResult = this.authenticationManager.authenticate(authRequest);
            System.out.println("Auth result: " + authResult + "With Username: " + authResult.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authResult);

            System.out.println("Authentication successful with ApiKey");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            if (this.ignoreFailure)
                filterChain.doFilter(request, response);
            else
                this.authenticationEntryPoint.commence(request, response, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
