package io.wulfcodes.secc.security.configurer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import io.wulfcodes.secc.filter.ApiKeyAuthenticationFilter;
import io.wulfcodes.secc.security.entrypoint.ApiKeyAuthenticationEntryPoint;

public class ApiKeyConfigurer extends AbstractHttpConfigurer<ApiKeyConfigurer, HttpSecurity> {

    private AuthenticationEntryPoint authenticationEntryPoint = new ApiKeyAuthenticationEntryPoint();
    private RequestMatcher apiKeyMatcher = new RequestHeaderRequestMatcher("X-API-Key");
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private SecurityContextRepository securityContextRepository;


    @Override
    public void init(HttpSecurity http) throws Exception {
        // 1. Get the Global Exception Handling Configurer
        ExceptionHandlingConfigurer<?> exceptionHandling = http.getConfigurer(ExceptionHandlingConfigurer.class);

        if (exceptionHandling != null) {
            // 2. Register YOUR entry point as the default
            // No fancy matchers needed. If this configurer is applied,
            // we assume this EntryPoint owns the errors.
            exceptionHandling.defaultAuthenticationEntryPointFor(this.authenticationEntryPoint, apiKeyMatcher);
        }
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter(authenticationManager, this.authenticationEntryPoint);
        if (this.authenticationDetailsSource != null) {

        }
        if (this.securityContextRepository != null) {

        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {

        }

//        apiKeyAuthenticationFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());

        // postProcess give this plain pojo, Spring Superpowers, it is now a managed spring bean
        apiKeyAuthenticationFilter = postProcess(apiKeyAuthenticationFilter);
        http.addFilterBefore(apiKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
