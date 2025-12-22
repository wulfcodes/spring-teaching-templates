package com.dj.jpa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dj.jpa.model.Grade;
import com.dj.jpa.repository.GradeRepository;

@Repository
public class GradeDao {

    @Autowired
    private GradeRepository gradeRepository;

    @Transactional
    public void updateMathGrade(Integer id, Float math) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        grade.setMath(math);
    }

    @Transactional
    public void updateScienceGrade(Integer id, Float science) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        grade.setScience(science);
    }

    @Transactional
    public void updateHistoryGrade(Integer id, Float history) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        grade.setHistory(history);
    }

    @Transactional
    public void updateEnglishGrade(Integer id, Float english) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        grade.setEnglish(english);
    }


    @Transactional
    public void updateRemark(String name, String remark) {
        Grade grade = gradeRepository.findByStudentName(name);
        grade.setRemark(remark);
    }

}
