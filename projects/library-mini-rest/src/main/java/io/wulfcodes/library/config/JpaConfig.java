package io.wulfcodes.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.wulfcodes.library.persistence.repo")
public class JpaConfig {
}
