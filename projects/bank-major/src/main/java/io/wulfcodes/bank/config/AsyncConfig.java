package io.wulfcodes.bank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Configuration to enable Async processing, Scheduling, and Retry capabilities.
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableRetry
public class AsyncConfig {
}
