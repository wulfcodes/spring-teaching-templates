package com.dj.jdbc.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dj.jdbc.model.Student;

@Repository
public class StudentDao1 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // The row-mapper is used to convert a single row in table into a java object
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getInt("age"), rs.getFloat("percentage"), rs.getBoolean("is_enrolled"));

    // Insert
    @Transactional
    public int save(Student student) {
        String query = """
                INSERT INTO student(first_name, last_name, email, age, percentage, is_enrolled)
                VALUES (?, ?, ?, ?, ?, ?)
               """;

        return jdbcTemplate.update(
            query,
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            student.getAge(),
            student.getPercentage(),
            student.getIsEnrolled()
        );
    }

    // Find
    public Student findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM student WHERE id = ?",
            studentRowMapper,
            id
        );
    }

    public Map<String, Object> findByIdRaw(Long id) {
        return jdbcTemplate.queryForMap(
            "SELECT * FROM student WHERE id = ?",
            id
        );
    }

    // Find All
    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * FROM student", studentRowMapper);
    }

    public List<Map<String, Object>> findAllRaw() {
        return jdbcTemplate.queryForList("SELECT * FROM student");
    }

    // Update a field
    @Transactional
    public int updateEmail(Long id, String newEmail) {
        return jdbcTemplate.update(
            "UPDATE student SET email = ? WHERE id = ?",
            newEmail, id
        );
    }

    // Delete
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
    }


    // Count
    public int countStudents() {
        return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM student",
            Integer.class
        );
    }

    // Find Multiple
    public List<Student> findEnrolledStudents() {
        return jdbcTemplate.query(
            "SELECT * FROM student WHERE is_enrolled = true",
            studentRowMapper
        );
    }

    public List<Map<String, Object>> findEnrolledStudentsRaw() {
        return jdbcTemplate.queryForList(
            "SELECT * FROM student WHERE is_enrolled = true"
        );
    }


    // Find One
    public Student findByEmail(String email) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM student WHERE email = ?",
            studentRowMapper,
            email
        );
    }


    public String findEmailRaw(String email) {
        return jdbcTemplate.queryForObject(
            "SELECT email FROM student WHERE email = ?",
            String.class,
            email
        );
    }

    // Find Multiple
    public List<Student> findByAgeGreaterThan(int age) {
        return jdbcTemplate.query(
            "SELECT * FROM student WHERE age > ?",
            studentRowMapper,
            age
        );
    }

    public List<Integer> findAgesGreaterThanRaw(int age) {
        return jdbcTemplate.queryForList(
            "SELECT age FROM student WHERE age > ?",
            Integer.class,
            age
        );
    }

    // Batch Insert
    @Transactional
    public void batchInsert(List<Student> students) {
        String query = """
                INSERT INTO student(first_name, last_name, email, age, percentage, is_enrolled)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.batchUpdate(query, students, students.size(), (ps, student) -> {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getAge());
            ps.setFloat(5, student.getPercentage());
            ps.setBoolean(6, student.getIsEnrolled());
        });
    }
}
