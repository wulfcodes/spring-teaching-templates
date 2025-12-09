package com.dj.jpa.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "grades")
public class Grade implements Serializable {

    private final long serialVersionUID = 4857161617975081156L;

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "student_name")
    private String studentName;

    private Float math;
    private Float english;
    private Float science;
    private Float history;

    private String remark;

    public Grade() {}



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Float getMath() {
        return math;
    }

    public void setMath(Float math) {
        this.math = math;
    }

    public Float getEnglish() {
        return english;
    }

    public void setEnglish(Float english) {
        this.english = english;
    }

    public Float getScience() {
        return science;
    }

    public void setScience(Float science) {
        this.science = science;
    }

    public Float getHistory() {
        return history;
    }

    public void setHistory(Float history) {
        this.history = history;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
