package io.wulfcodes.filinc.interceptor;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import io.wulfcodes.filinc.annotation.RateLimit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    // Better to use Cache like Redis
    private final Map<String, Map<String, Long>> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);



            if (rateLimit != null) {
                String origin = request.getHeader(HttpHeaders.ORIGIN);
                int maxRequestCounts = rateLimit.maxRequestCounts();
                long duration = Duration.ofSeconds(rateLimit.duration()).toMillis();

                Map<String, Long> requestBucket = buckets.compute(origin, (key, currentBucket) -> {
                    long currentTimestamp = System.currentTimeMillis();
                    if (Objects.isNull(currentBucket) || currentTimestamp > currentBucket.get("expiryTimestamp")) {
                        Map<String, Long> newBucket = new ConcurrentHashMap<>();
                        newBucket.put("requestCount", 1L);
                        newBucket.put("expiryTimestamp", currentTimestamp + duration);
                        return newBucket;
                    }
                    currentBucket.merge("requestCount", 1L, Math::addExact);
                    return currentBucket;
                });

                if (requestBucket.get("requestCount").intValue() > maxRequestCounts) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(Duration.ofMillis(requestBucket.get("expiryTimestamp") - System.currentTimeMillis()).getSeconds()));
                    return false;
                }
            }
        }
        return true;
    }


}
