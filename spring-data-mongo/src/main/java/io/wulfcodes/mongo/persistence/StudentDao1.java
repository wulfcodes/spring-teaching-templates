package io.wulfcodes.mongo.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.wulfcodes.mongo.model.Student;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Repository
public class StudentDao1 {

    @Autowired
    private MongoTemplate mongoTemplate;

    /* INSERT Data */
    public Student insertStudent(Student student) {
        return mongoTemplate.insert(student); // only save new entity
    }

    public Student saveStudent(Student student) {
        return mongoTemplate.save(student); // save new and update existing
    }

    public List<Student> batchSaveStudents(List<Student> students) {
        return (List<Student>) mongoTemplate.insert(students, Student.class);
    }

    /* QUERY Data */
    public Student findById(String id) {
        return mongoTemplate.findById(id, Student.class);
    }

    public List<Student> find() {
        return mongoTemplate.findAll(Student.class);
    }

    public Student findByEmail(String email) {
        Criteria criteria = new Criteria("email");
        criteria.is(email);

        Query query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.findOne(query, Student.class);
    }

    public List<Student> findAllByName(String name) {
        Criteria criteria = new Criteria("name");
        criteria.is(name);

        Query query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.find(query, Student.class);
    }

    public List<Student> findAllByAgeGreaterThan(Integer age) {
        return mongoTemplate.find(
            Query.query(Criteria.where("age").gt(age)),
            Student.class
        );
    }

    public List<Student> findTopYoungStudents() {
        return mongoTemplate.find(
            Query.query(
                     Criteria.where("isActive").is(true)
                             .and("age").lte(20)
                             .and("gpa").gt(3.5)
                 )
                 .with(Sort.by(DESC, "gpa")),
            Student.class
        );
    }

    public List<Student> findByNameStartingWith(String prefix) {
        return mongoTemplate.find(
            Query.query(Criteria.where("name").regex("^" + prefix, "i")),
            Student.class
        );
    }

    public List<Student> findScienceStudents() {
        return mongoTemplate.find(
          Query.query(Criteria.where("subjects").all("Physics", "Chemistry")),
          Student.class
        );
    }

    public List<Student> findByZip(int zip) {
        return mongoTemplate.find(
            Query.query(Criteria.where("address.zipCode").is(zip)),
            Student.class
        );
    }

    /* UPDATE Data */
    public void updateStudent(Student student) {
        mongoTemplate.save(student); // replaces the current student entirely if present, else create a new student
    }

    public void upsertStudentGpaByEmail(String email, double gpa) {
        mongoTemplate.upsert( // updates specific field, caveat: creates a completely new entry with the fields, if no matching entry found (setOnInsert is used for that)
            Query.query(Criteria.where("email").is(email)),
            Update.update("gpa", gpa)
                  .set("isActive", true)
                  .setOnInsert("enrolledOn", LocalDate.now()),
            Student.class
        );
    }

    public void updateEmail(String oldEmail, String newEmail) {
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("email").is(oldEmail)),
            Update.update("email", newEmail),
            Student.class
        );
    }

    public void updateStatus() {
        mongoTemplate.updateMulti(
            Query.query(Criteria.where("gpa").lt(2.0)),
            Update.update("isActive", false),
            Student.class
        );
    }

    // Simple Update: Change status of a student
    public void updateStatus(String id, boolean status) {
        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("isActive", status); // $set
        update.currentDate("lastModified"); // Sets current timestamp

        UpdateResult result = mongoTemplate.updateFirst(query, update, Student.class);
    }

    // Array Modification: Add a new subject (Push)
    public void addSubject(String id, String newSubject) {
        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();
        update.push("subjects", newSubject); // $push

        UpdateResult result = mongoTemplate.updateFirst(query, update, Student.class);
    }

    // Atomic Update: Increment age and return the NEW object
    public void incrementAge(String id) {
        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();
        update.inc("age", 1); // $inc (Atomic increment)

        UpdateResult result = mongoTemplate.updateFirst(query, update, Student.class);
    }

    /* DELETE Data */
    public void deleteInactiveStudents() {
        Query query = new Query(Criteria.where("isActive").is(false));

        DeleteResult result = mongoTemplate.remove(query, Student.class);
    }

}
