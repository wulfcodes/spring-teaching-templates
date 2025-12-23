package io.wulfcodes.filinc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import io.wulfcodes.filinc.annotation.MaintenanceMode;

@Component
public class MaintenanceResourceInterceptor implements HandlerInterceptor {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            MaintenanceMode maintenanceMode = handlerMethod.getMethodAnnotation(MaintenanceMode.class);
            if (maintenanceMode != null) {
                response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
                return false;
            }
        }
        return true;
    }

}
