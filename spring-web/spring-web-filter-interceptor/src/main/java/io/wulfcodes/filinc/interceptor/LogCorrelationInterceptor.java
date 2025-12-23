package io.wulfcodes.filinc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.MDC;

@Component
public class LogCorrelationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = request.getHeader("X-Correlation-ID");
        if (!StringUtils.hasLength(correlationId))
            correlationId = java.util.UUID.randomUUID().toString();

        MDC.put("correlationId", correlationId);

        response.addHeader("X-Correlation-ID", correlationId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove("correlationId");
    }

}
