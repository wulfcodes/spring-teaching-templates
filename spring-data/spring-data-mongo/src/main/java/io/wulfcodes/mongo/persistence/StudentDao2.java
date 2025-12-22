package io.wulfcodes.mongo.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import io.wulfcodes.mongo.model.Student;

@Repository
public class StudentDao2 {

    @Autowired
    private StudentRepository studentRepository;

    /* Pre-defined Methods */
    public Student saveStudent(Student student) {
        return studentRepository.save(student); // saves new student, else replaces existing student
    }

    public Student insertStudent(Student student) {
        return studentRepository.insert(student);
    }

    public List<Student> saveAll(List<Student> students) {
        return studentRepository.saveAll(students);
    }

    public List<Student> insertAll(List<Student> students) {
        return studentRepository.insert(students);
    }

    public Optional<Student> findById(String id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public boolean existsById(String id) {
        return studentRepository.existsById(id);
    }

    public long countAll() {
        return studentRepository.count();
    }

    public Optional findByExample(Example<Student> example) {
        return studentRepository.findOne(example);
    }

    public void deleteById(String id) {
        studentRepository.deleteById(id);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }

    public void deleteAll(List<Student> students) {
        studentRepository.deleteAll(students);
    }


}
