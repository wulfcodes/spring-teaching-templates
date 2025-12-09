package com.dj.core.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.basic")
public class AppBasicProps {

    private String name;
    private Boolean developer;
    private Integer age;
    private Double marks;

    public AppBasicProps() {}

    public AppBasicProps(String name, Boolean developer, Integer age) {
        this.name = name;
        this.developer = developer;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDeveloper() {
        return developer;
    }

    public void setDeveloper(Boolean developer) {
        this.developer = developer;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getDeveloper() {
        return developer;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }
}
