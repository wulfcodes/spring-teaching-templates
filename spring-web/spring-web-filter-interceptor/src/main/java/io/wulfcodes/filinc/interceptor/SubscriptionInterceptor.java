package io.wulfcodes.filinc.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SubscriptionInterceptor implements HandlerInterceptor {

    // Database like MySQL should be used
    private static final Map<String, Double> userBalances = new ConcurrentHashMap<>(
        Map.of("Swayam", 5.0)
    );

    private static final Double maxValuePerRequest = 2.5;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {

            // User to be fetched from Session or Security Context and Authorized using @PreAuthorize first for PREMIUM roles etc (Spring Security)
            String username = request.getParameter("username");
            double currentBalance = userBalances.getOrDefault("Swayam", 0.0);
            if (currentBalance < maxValuePerRequest) {
                response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod handlerMethod) {
            String username = request.getParameter("username");
            userBalances.merge(username, maxValuePerRequest, (currBalance, usedBalance) -> currBalance - usedBalance);
        }
    }

}
