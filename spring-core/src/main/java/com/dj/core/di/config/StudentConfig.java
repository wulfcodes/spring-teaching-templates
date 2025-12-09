package com.dj.core.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dj.core.di.dependency.Student;

@Configuration
public class StudentConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("DJ");
        return student;
    }

}
