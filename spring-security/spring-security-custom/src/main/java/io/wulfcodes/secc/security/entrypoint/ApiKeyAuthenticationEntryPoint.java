package io.wulfcodes.secc.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class ApiKeyAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("ApiKeyAuthenticationEntryPoint called");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "ApiKey realm=\"Access to Protected API\"");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write("""
//                {
//                    "error": "Unauthorized",
//                    "message": "%s"
//                }
//                """.formatted(authException.getMessage()));
    }
}
