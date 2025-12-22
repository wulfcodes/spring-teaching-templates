package com.dj.jpa.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dj.jpa.model.Teacher;
import com.dj.jpa.repository.TeacherRepository;

@Repository
public class TeacherDao1 {

    @Autowired
    private TeacherRepository teacherRepository;

    @Transactional
    public Teacher upsertOne(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Transactional
    public List<Teacher> upsertBatch(List<Teacher> teachers) {
        return teacherRepository.saveAll(teachers);
    }

    public long getCount() {
        return teacherRepository.count();
    }

    public boolean checkExistenceById(int id) {
        return teacherRepository.existsById(id);
    }

    public boolean checkExistenceByExample(Example<Teacher> example) {
        return teacherRepository.exists(example);
    }

    public Teacher getById(int id) {
        return teacherRepository.findById(id).get();
    }

    public Teacher getByExample(Example<Teacher> example) {
        return teacherRepository.findOne(example).get();
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public List<Teacher> getAllByExample(Example<Teacher> example) {
        return teacherRepository.findAll(example);
    }

    @Transactional
    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEntity(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Transactional
    public void deleteAll() {
        teacherRepository.deleteAll();
    }

}
