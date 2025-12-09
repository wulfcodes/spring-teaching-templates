package com.dj.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dj.core.di.dependency.Student;

@Component
public class StudentUser {

    @Autowired
    private Student student;

    public void useStudent() {
        System.out.println("The student is " + student.getName());
    }
}
