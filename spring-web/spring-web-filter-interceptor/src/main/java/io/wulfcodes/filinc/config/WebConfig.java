package io.wulfcodes.filinc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.wulfcodes.filinc.filter.TestFilter2;
import io.wulfcodes.filinc.interceptor.LogCorrelationInterceptor;
import io.wulfcodes.filinc.interceptor.TestInterceptor1;
import io.wulfcodes.filinc.interceptor.TestInterceptor2;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TestInterceptor1 testInterceptor1;

    @Autowired
    private TestInterceptor2 testInterceptor2;


    @Bean
    public FilterRegistrationBean<TestFilter2> test2FilterRegistrationBean() {
        FilterRegistrationBean<TestFilter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TestFilter2());
        registrationBean.addUrlPatterns("/test2");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor1).order(1);
        registry.addInterceptor(testInterceptor2).addPathPatterns("/test2").order(2);

    }

}
