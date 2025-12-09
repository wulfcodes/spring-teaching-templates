package com.dj.jdbc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.dj.jdbc.dao.StudentDao1;
import com.dj.jdbc.model.Student;

@Component
public class JdbcApplicationRunner implements CommandLineRunner {

    @Autowired
    private StudentDao1 studentDao1;

    @Override
    public void run(String... args) throws Exception {
        List<Student> studentList = studentDao1.findAll();
    }
}
