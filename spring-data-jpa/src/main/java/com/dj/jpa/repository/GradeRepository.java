package com.dj.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import com.dj.jpa.model.Grade;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Lock(PESSIMISTIC_WRITE)
    Grade findByStudentName(String studentName);

}
