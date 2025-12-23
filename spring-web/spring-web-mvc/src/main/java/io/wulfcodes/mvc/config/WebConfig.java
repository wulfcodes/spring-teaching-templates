package io.wulfcodes.mvc.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.samskivert.mustache.Mustache;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public MustacheViewResolver mustacheViewResolver(Mustache.Compiler compiler) {
        MustacheViewResolver resolver = new MustacheViewResolver(compiler);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".mustache");
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(2);
        return resolver;
    }

}
