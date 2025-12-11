package io.wulfcodes.library.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request,
        @NonNull
        HttpServletResponse response,
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userData") == null) {
            response.sendRedirect("/auth/sign-in");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth") || path.startsWith("/assets");
    }
}
