package io.wulfcodes.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories(basePackages = "io.wulfcodes.library.persistence.repo")
public class JdbcConfig {
}
