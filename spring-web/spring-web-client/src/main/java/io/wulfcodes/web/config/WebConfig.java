package io.wulfcodes.web.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    private final String TYPICODE_BASE_URL = "https://jsonplaceholder.typicode.com";

    @Bean
    @Qualifier("typicodeTemplate")
    public RestTemplate typicodeRestTemplate(RestTemplateBuilder builder) {
        return builder
            .rootUri(TYPICODE_BASE_URL)
            .connectTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(10))
            .defaultHeader(HttpHeaders.USER_AGENT, "SpringClientDemo/0.0.1-SNAPSHOT (Java 21; Spring Boot 3.5)")
            .build();
    }

    @Bean
    @Qualifier("typicodeClient")
    public RestClient typicodeRestClient(RestClient.Builder builder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));

        return builder
            .baseUrl(TYPICODE_BASE_URL)
            .requestFactory(factory)
            .defaultHeader(HttpHeaders.USER_AGENT, "SpringClientDemo/0.0.1-SNAPSHOT (Java 21; Spring Boot 3.5)")
//            .defaultCookie()
            .build();
    }

}
