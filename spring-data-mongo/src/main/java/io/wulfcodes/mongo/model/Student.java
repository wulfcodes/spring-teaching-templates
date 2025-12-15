package io.wulfcodes.mongo.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "students")
public class Student {

    @Version
    private Long version;

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;
    private Integer age;
    @Field("gradePointAverage")
    private Double gpa;
    private Boolean isActive;

    private LocalDate enrolledOn;
    private List<String> subjects;

    @ReadOnlyProperty
    private Map<String, String> metadata;

    //  For @DocumentReference need to annotate address with @Document, and address is treated as a separate collection
    // Always two queries fired, one for student, one lazily for address
    // commenting this out means, address is not a separate collection, rather a nested object
    // @DocumentReference
    private Address address;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Student() {}

    @PersistenceCreator
    public Student(Long version, String id, String email, String name, Integer age, Double gpa, Boolean isActive, LocalDate enrolledOn, List<String> subjects, Map<String, String> metadata, Address address, Instant createdAt, Instant updatedAt) {
        this.version = version;
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.isActive = isActive;
        this.enrolledOn = enrolledOn;
        this.subjects = subjects;
        this.metadata = metadata;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getEnrolledOn() {
        return enrolledOn;
    }

    public void setEnrolledOn(LocalDate enrolledOn) {
        this.enrolledOn = enrolledOn;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
