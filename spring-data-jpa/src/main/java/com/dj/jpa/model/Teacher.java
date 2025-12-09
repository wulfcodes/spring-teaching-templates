package com.dj.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {

    private final long serialVersionUID = -2402506515042756331L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "is_qualified")
    private Boolean isQualified;

    @Column(scale = 2)
    private Double salary;

    @Enumerated(STRING)
    private Subject subject;

    @Column(name = "joining_date", nullable = false, updatable = false)
    private LocalDate joiningDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Teacher() {}

    public Teacher(String name, Integer age, String email, Boolean isQualified, Double salary, Subject subject) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.isQualified = isQualified;
        this.salary = salary;
        this.subject = subject;
    }

    @PrePersist
    private void atSave() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void atUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getQualified() {
        return isQualified;
    }

    public void setQualified(Boolean qualified) {
        isQualified = qualified;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age='" + age + '\'' +
            ", email='" + email + '\'' +
            ", isQualified=" + isQualified +
            ", salary=" + salary +
            ", subject=" + subject +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
