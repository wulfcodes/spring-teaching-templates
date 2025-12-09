package com.dj.core.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropUser {

    @Value("${app.prop.name}")
    private String name;

    @Value("${app.prop.developer}")
    private boolean developer;

    @Value("${app.prop.age}")
    private int age;

    @Autowired
    private AppBasicProps appBasicProps;

    @Autowired
    private AppComplexProps appComplexProps;

    public void checkProps() {
        System.out.println("Name: " + name + "\nDeveloper: " + developer + "\nAge: " + age);
    }

    public void checkBasicProps() {
        System.out.println("Name: " + appBasicProps.getName() + "\nDeveloper: " + appBasicProps.isDeveloper() + "\nAge: " + appBasicProps.getAge() + "\nMarks: " + appBasicProps.getMarks());
    }

    public void checkComplexProps() {
        System.out.println("External Servers: " + appComplexProps.getExternalServers());
        System.out.println("Internal Servers: " + appComplexProps.getInternalServers());
        System.out.println("Stats: " + appComplexProps.getStats());
        System.out.println("Timeout: " + appComplexProps.getTimeout());
        System.out.println("File Size: " + appComplexProps.getFileSize());
        System.out.println("Domain: " + appComplexProps.getDomain());
    }

}
