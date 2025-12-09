package com.dj.jdbc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("students")
public class Student {

    @Id
    private Long id;

    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String email;
    private Integer age;
    private Float percentage;

    @Column("is_enrolled")
    private Boolean isEnrolled;

    @Transient
    private String fullName;

    public Student() {}

    public Student(String firstName, String lastName, String email, Integer age, Float percentage, Boolean isEnrolled, String fullName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.percentage = percentage;
        this.isEnrolled = isEnrolled;
        this.fullName = fullName;
    }

    @PersistenceCreator
    public Student(Long id, String firstName, String lastName, String email, Integer age, Float percentage, Boolean isEnrolled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.percentage = percentage;
        this.isEnrolled = isEnrolled;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Float getPercentage() { return percentage; }
    public void setPercentage(Float percentage) { this.percentage = percentage; }

    public Boolean getIsEnrolled() { return isEnrolled; }
    public void setIsEnrolled(Boolean isEnrolled) { this.isEnrolled = isEnrolled; }

    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", age=" + age +
            ", percentage=" + percentage +
            ", isEnrolled=" + isEnrolled +
            '}';
    }
}
