package com.dj.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dj.jdbc.model.Student;
import com.dj.jdbc.repository.StudentRepository;

@Repository
public class StudentDao3 {

    @Autowired
    private StudentRepository studentRepository;

    // Insert
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    // Find
    public Student findById(Long id) {
        return studentRepository.findById(id).get();
    }

    // Find All
    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    // Update a field
    @Transactional
    public Student updateEmail(Long id, String newEmail) {
        Student student = studentRepository.findById(id).get();
        student.setEmail(newEmail);
        return studentRepository.save(student);
    }

    // Delete
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

}
