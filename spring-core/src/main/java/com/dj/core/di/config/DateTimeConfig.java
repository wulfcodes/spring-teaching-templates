package com.dj.core.di.config;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateTimeConfig {

    @Bean
    public LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }

}
