package io.wulfcodes.filinc.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

public class TestFilter2 extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request,
        @NonNull
        HttpServletResponse response,
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("Step 1.1: Raw Request in filter2 for pre-processing");

        filterChain.doFilter(request, response);

        System.out.println("Step 6.1: Modified Request and Raw Response in filter2 for post-processing");
    }
}
