package com.dj.jpa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // --- 1. One-to-One with Address ---
    /* Cascade: ALL (If we delete the student, we want the address gone too).
       Fetch: EAGER (When we load a Student, we usually want their address immediately).
    */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // --- 2. Many-to-Many with Course ---
    /* Cascade: PERSIST, MERGE (We NEVER use REMOVE here. Deleting a student 
       must not delete the Physics course from the database!).
       Fetch: LAZY (Don't load all history courses just because we loaded one student).
    */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course_enrollment",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    // --- 3. One-to-Many with Mark ---
    /* Cascade: ALL (If the student leaves the school, their marks are irrelevant).
       Fetch: LAZY (A student might have 1000s of marks; load them only when asked).
       OrphanRemoval: True (If we remove a Mark from this list, delete it from DB).
    */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Mark> marks = new ArrayList<>();

    // Helper Methods
    public void addMark(Mark mark) {
        marks.add(mark);
        mark.setStudent(this);
    }
    
    
}