package io.wulfcodes.filinc.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class TestFilter1 extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request,
        @NonNull
        HttpServletResponse response,
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("Step 1: Raw Request in filter1 for pre-processing");

        filterChain.doFilter(request, response);

        System.out.println("Step 7: Modified Request and Raw Response in filter1 for post-processing");
    }
}
