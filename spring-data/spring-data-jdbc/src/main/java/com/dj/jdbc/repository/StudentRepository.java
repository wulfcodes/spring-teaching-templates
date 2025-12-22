package com.dj.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dj.jdbc.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {}
