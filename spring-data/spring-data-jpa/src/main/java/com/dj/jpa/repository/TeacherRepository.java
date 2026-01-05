package com.dj.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dj.jpa.model.Subject;
import com.dj.jpa.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    /* Derived Query Methods */
    Teacher findByEmail(String email);
    List<Teacher> findByName(String name);
    List<Teacher> findByNameAndAge(String name, Integer age);
    List<Teacher> findByNameOrAge(String name, Integer age);

    List<Teacher> findByAgeGreaterThan(int age);
    List<Teacher> findByAgeLessThan(int age);
    List<Teacher> findByAgeBetween(int start, int end);
    List<Teacher> findBySalaryGreaterThanEqual(double salary);

    List<Teacher> findByJoiningDateAfter(LocalDate date);
    List<Teacher> findByJoiningDateBefore(LocalDate date);
    List<Teacher> findByJoiningDateBetween(LocalDate startDate, LocalDate endDate);

    List<Teacher> findByNameContaining(String keyword);   // LIKE %keyword%
    List<Teacher> findByNameStartingWith(String prefix);  // LIKE prefix%
    List<Teacher> findByNameEndingWith(String suffix);    // LIKE %suffix
    List<Teacher> findByNameIgnoreCase(String name);

    List<Teacher> findBySubject(Subject subject);
    List<Teacher> findBySubjectIn(List<Subject> subjects);
    List<Teacher> findBySubjectNot(Subject subject);

    List<Teacher> findByIsQualifiedTrue();
    List<Teacher> findByIsQualifiedFalse();

    List<Teacher> findByNameOrderByAgeAsc(String name);
    List<Teacher> findByAgeOrderBySalaryDesc(Integer age);

    List<Teacher> findBySalaryIsNull();
    List<Teacher> findBySalaryIsNotNull();

    boolean existsByName(String name);

    long countByName(String name);
    long countByAgeGreaterThan(int age);

    @Modifying
    void deleteByName(String name);

    /* JPQL Query Methods */
    @Query("SELECT t FROM Teacher t")
    List<Teacher> getAll();

    @Query("SELECT t.name FROM Teacher t")
    List<String> getAllNames();

    @Query("SELECT t FROM Teacher t WHERE t.age > :age")
    List<Teacher> getOlderThan(@Param("age") int age);

    @Query("SELECT t FROM Teacher t WHERE t.isQualified = true")
    List<Teacher> getQualifiedTeachers();

    @Query("SELECT t FROM Teacher t WHERE t.name LIKE :prefix%")
    List<Teacher> getByNameStartingWith(@Param("prefix") String prefix);

    @Query("SELECT t FROM Teacher t WHERE t.name LIKE %:word%")
    List<Teacher> getByNameContaining(@Param("word") String word);

    @Query("SELECT t FROM Teacher t WHERE t.subject = :subject")
    List<Teacher> getBySubject(@Param("subject") Subject subject);

    @Query("SELECT t FROM Teacher t WHERE t.age BETWEEN ?1 AND ?2")
    List<Teacher> getByAgeRange(Double minAge, Double maxAge);

    @Query("SELECT t FROM Teacher t WHERE t.salary BETWEEN :minSalary AND :maxSalary")
    List<Teacher> getBySalaryRange(Double minSalary, Double maxSalary);

    @Query("SELECT t FROM Teacher t WHERE t.joiningDate > :date")
    List<Teacher> getJoinedAfter(LocalDate date);

    @Query("SELECT t FROM Teacher t ORDER BY t.salary DESC")
    List<Teacher> getBySalaryDesc();

    @Query("SELECT COUNT(t) FROM Teacher t")
    Long getTotalTeachers();

    @Query("SELECT SUM(t.salary) FROM Teacher t")
    Double getTotalSalary();

    @Query("SELECT AVG(t.salary) FROM Teacher t")
    Double getAverageSalary();

    @Query("SELECT t FROM Teacher t WHERE t.age > ?1 AND t.salary < ?2")
    List<Teacher> getByAgeAndSalary(Integer age, Double salary);

    @Modifying
    @Query("UPDATE Teacher t SET t.salary = :salary WHERE t.id = :id")
    int updateSalary(Integer id, Double salary);

    @Modifying
    @Query("DELETE FROM Teacher t WHERE t.email = :email")
    int deleteByEmail(String email);

    /* Native Query Methods */
    @Query(
        value = "SELECT * FROM teachers WHERE salary > :salary AND subject = :subject",
        nativeQuery = true
    )
    List<Teacher> findBySalaryAndSubject(
        @Param("salary") Double salary,
        @Param("subject") String subject
    );

}
