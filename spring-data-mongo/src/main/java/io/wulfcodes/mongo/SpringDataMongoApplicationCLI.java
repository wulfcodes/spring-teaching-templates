package io.wulfcodes.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.wulfcodes.mongo.model.Student;
import io.wulfcodes.mongo.persistence.StudentDao1;

@Component
public class SpringDataMongoApplicationCLI implements CommandLineRunner {

    @Autowired
    private StudentDao1 studentDao1;

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student();
        student.setEmail("sam@hotmail.com");
        student.setActive(true);
        student.setName("Sam Nayak");
        student.setGpa(4.6);
        student.setAge(45);

        studentDao1.saveStudent(student);
    }
}
