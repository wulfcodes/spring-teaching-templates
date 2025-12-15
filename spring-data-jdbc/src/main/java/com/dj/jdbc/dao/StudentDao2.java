package com.dj.jdbc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dj.jdbc.model.Student;

@Repository
public class StudentDao2 {

    @Autowired
    private JdbcClient jdbcClient;

    // Row Mapper
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getInt("age"), rs.getFloat("percentage"), rs.getBoolean("is_enrolled"));


    // Insert
    @Transactional
    public int save(Student student) {
        return jdbcClient.sql("""
                                 INSERT INTO student(first_name, last_name, email, age, percentage, is_enrolled)
                                 VALUES(:first, :last, :email, :age, :percentage, :isEnrolled)
                             """)
                         .param("first", student.getFirstName())
                         .param("last", student.getLastName())
                         .param("email", student.getEmail())
                         .param("age", student.getAge())
                         .param("percentage", student.getPercentage())
                         .param("isEnrolled", student.getIsEnrolled())
                         .update();
    }

    // Find
    public Student findById(Long id) {
        return jdbcClient.sql("SELECT * FROM students WHERE id = :id")
                         .param("id", id)
                         .query(studentRowMapper)
                         .single();
    }

    public Map<String, Object> findByIdRaw(Long id) {
        return jdbcClient.sql("SELECT * FROM students WHERE id = :id")
                         .param("id", id)
                         .query()
                         .singleRow();
    }

    // Find All
    public List<Student> findAll() {
        return jdbcClient.sql("SELECT * FROM student")
                         .query(studentRowMapper)
                         .list();
    }

    public List<Map<String, Object>> findAllRaw() {
        return jdbcClient.sql("SELECT * FROM student")
                         .query()
                         .listOfRows();
    }

    // Update a field
    @Transactional
    public int updateEmail(Long id, String newEmail) {
        return jdbcClient.sql("UPDATE students SET email = :email WHERE id = :id")
                         .param("email", newEmail)
                         .param("id", id)
                         .update();
    }

    // Delete
    public int deleteById(Long id) {
        return jdbcClient.sql("DELETE FROM students WHERE id = :id")
                         .param("id", id)
                         .update();
    }

    // Count
    public int countStudents() {
        return jdbcClient.sql("SELECT COUNT(*) FROM student")
                         .query(Integer.class)
                         .single();
    }

   // Find Multiple
    public List<Student> findEnrolledStudents() {
        return jdbcClient.sql("SELECT * FROM students WHERE is_enrolled = true")
                         .query(studentRowMapper)
                         .list();
    }

    public List<Map<String, Object>> findEnrolledStudentsRaw() {
        return jdbcClient.sql("SELECT * FROM students WHERE is_enrolled = true")
                         .query()
                         .listOfRows();
    }

    // Find One
    public Student findByEmail(String email) {
        return jdbcClient.sql("SELECT * FROM students WHERE email = :email")
                         .param("email", email)
                         .query(studentRowMapper)
                         .single();
    }

    public String findEmailRaw(String email) {
        return jdbcClient.sql("SELECT email FROM students WHERE email = :email")
                         .param("email", email)
                         .query(String.class)
                         .single();
    }

    // Find Multiple
    public List<Student> findByAgeGreaterThan(int age) {
        return jdbcClient.sql("SELECT * FROM students WHERE age > :age")
                         .param("age", age)
                         .query(studentRowMapper)
                         .list();
    }

    public List<Integer> findAgesGreaterThanRaw(int age) {
        return jdbcClient.sql("SELECT age FROM students WHERE age > :age")
                         .param("age", age)
                         .query(Integer.class)
                         .list();
    }

    // Batch Insert
    @Transactional
    public void batchInsert(List<Student> students) {
        jdbcClient.sql("""
                          INSERT INTO student(first_name, last_name, email, age, percentage, is_enrolled)
                          VALUES(:first, :last, :email, :age, :percentage, :isEnrolled)
                      """)
                  .params(students.stream().map(s -> Map.of(
                      "first", s.getFirstName(),
                      "last", s.getLastName(),
                      "email", s.getEmail(),
                      "age", s.getAge(),
                      "percentage", s.getPercentage(),
                      "isEnrolled", s.getIsEnrolled()
                  )).toList())
                  .update();
    }
}

