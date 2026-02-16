package io.wulfcodes.secc.interceptor;

import io.wulfcodes.secc.common.context.UserContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Autowired
    private UserContext userContext;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = (String)authentication.getPrincipal();
            userContext.setUsername(username);
            userContext.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        }

        return true;
    }
}
