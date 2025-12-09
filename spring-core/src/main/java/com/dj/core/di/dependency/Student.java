package com.dj.core.di.dependency;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private String name = "Unknown";

    public Student() {
        System.out.println("I don't have a name, please update my name after object creation");
    }

    public void setName(String name) {
        System.out.println("Thank you for updating my name");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
