package io.wulfcodes.filinc.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class PayloadSizeFilter extends OncePerRequestFilter {

    private static final long MAX_SIZE = 1024 * 1024; // 1MB limit

    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request,
        @NonNull
        HttpServletResponse response,
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {
        String contentLength = request.getHeader(HttpHeaders.CONTENT_LENGTH);
        if (!StringUtils.hasLength(contentLength)) {
            response.setStatus(HttpStatus.LENGTH_REQUIRED.value());
            return;
        }

        long requestSize = Long.parseLong(contentLength);
        if (requestSize > MAX_SIZE) {
            response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
