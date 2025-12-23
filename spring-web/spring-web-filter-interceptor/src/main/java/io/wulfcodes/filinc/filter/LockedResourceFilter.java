package io.wulfcodes.filinc.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class LockedResourceFilter extends OncePerRequestFilter {
    // Spring's utility for matching paths like "/api/orders/**"
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Value("${app.locked-routes}")
    private List<String> lockedRoutes;


    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request,
        @NonNull
        HttpServletResponse response,
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Check if the current path matches any locked pattern

        if (lockedRoutes.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestPath))) {
            response.setStatus(HttpStatus.LOCKED.value());
            response.getWriter().write("This service is currently under locked.");
            return; // STOP the chain here. Do not call filterChain.doFilter()
        }

        filterChain.doFilter(request, response);
    }
}
