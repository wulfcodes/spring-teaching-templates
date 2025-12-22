package io.wulfcodes.mongo.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import io.wulfcodes.mongo.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    // ==========================================
    // 1. EXACT MATCHING & EQUALITY
    // ==========================================

    // Find by exact email (Unique)
    Optional<Student> findByEmail(String email);

    // Find by name (List, since names might duplicate)
    List<Student> findByName(String name);

    // Find by Active status (Boolean)
    List<Student> findByIsActiveTrue();
    List<Student> findByIsActiveFalse();

    // Find where Age is NULL (e.g., data issue)
    List<Student> findByAgeIsNull();


    // ==========================================
    // 2. COMPARISON & RANGES (Numbers/Dates)
    // ==========================================

    // Greater/Less Than
    List<Student> findByGpaGreaterThanEqual(Double gpa);
    List<Student> findByAgeLessThan(Integer age);

    // Between (Inclusive usually, depends on DB engine)
    List<Student> findByAgeBetween(Integer min, Integer max);

    // Dates: Find students enrolled BEFORE a certain date
    List<Student> findByEnrolledOnBefore(LocalDate date);

    // Dates: Find students enrolled AFTER a certain date
    List<Student> findByEnrolledOnAfter(LocalDate date);


    // ==========================================
    // 3. STRING MATCHING (Regex/Wildcards)
    // ==========================================

    // Starts With (e.g., "Jo" -> "John", "Joseph") - Efficient (uses index)
    List<Student> findByNameStartingWith(String prefix);

    // Ends With (e.g., "son" -> "Jason", "Tyson") - Slow (scan)
    List<Student> findByNameEndingWith(String suffix);

    // Containing (Like %str%) - Slow (scan)
    List<Student> findByNameContaining(String partialName);

    // Ignore Case (Case Insensitive Search)
    List<Student> findByNameIgnoreCase(String name);


    // ==========================================
    // 4. LOGICAL OPERATORS (AND / OR)
    // ==========================================

    // Find Active students AND older than X
    List<Student> findByIsActiveTrueAndAgeGreaterThan(Integer age);

    // Find by Email OR Name (Search functionality)
    List<Student> findByEmailOrName(String email, String name);


    // ==========================================
    // 5. ARRAY & COLLECTION QUERIES (subjects)
    // ==========================================

    // Find students who have "Math" in their subjects list
    List<Student> findBySubjectsContaining(String subject);

    // Same as above, just different syntax often used for scalar lists
    List<Student> findBySubjectsIs(String subject);

    // Find students whose subjects match ANY in the provided list (OR logic)
    List<Student> findBySubjectsIn(List<String> subjects);


    // ==========================================
    // 6. NESTED / EMBEDDED FIELDS (Address)
    // ==========================================

    // Traverses Student -> Address -> City
    List<Student> findByAddressCity(String city);

    // Traverses Student -> Address -> ZipCode
    List<Student> findByAddressZipCode(String zipCode);


    // ==========================================
    // 7. SORTING & LIMITING (Top/First)
    // ==========================================

    // Find all, sort by GPA Descending (Smartest first)
    List<Student> findByIsActiveTrueOrderByGpaDesc();

    // Get ONLY the top 3 oldest students
    List<Student> findTop3ByOrderByAgeDesc();

    // Find first student by name
    Optional<Student> findFirstByName(String name);


    // ==========================================
    // 8. PAGINATION & SLICING
    // ==========================================

    // Standard Pagination (Returns data + total count query)
    Page<Student> findByIsActiveTrue(Pageable pageable);

    // Slice (Returns data + "hasNext" flag only) - Faster for infinite scroll
    Slice<Student> findByAgeGreaterThan(Integer age, Pageable pageable);


    // ==========================================
    // 9. DELETE & COUNT
    // ==========================================

    // Count how many students are active
    long countByIsActiveTrue();

    // Delete inactive students (Returns number of deleted docs)
    long deleteByIsActiveFalse();

    // Delete by email and return the deleted list
    List<Student> removeByEmail(String email);




    // =================================================================
    // 1. COMPARISON OPERATORS (@Query)
    // =================================================================

    // Equality (Standard)
    @Query("{ 'age' : ?0 }")
    List<Student> findByAge(String age);

    // Not Equal ($ne)
    @Query("{ 'name' : { $ne : ?0 } }")
    List<Student> findByNameNotEqual(String name);

    // Greater Than ($gt)
    @Query("{ 'age' : { $gt : ?0 } }")
    List<Student> findByAgeGreaterThan(Integer age);

    // Less Than or Equal ($lte)
    @Query("{ 'gpa' : { $lte : ?0 } }")
    List<Student> findByGpaLow(Double maxGpa);

    // Range ($gt and $lt)
    @Query("{ 'age' : { $gt : ?0, $lt : ?1 } }")
    List<Student> findByAgeRange(Integer min, Integer max);

    // In List ($in)
    @Query("{ 'address.city' : { $in : ?0 } }")
    List<Student> findByCities(List<String> cities);

    // Not In List ($nin)
    @Query("{ 'address.city' : { $nin : ?0 } }")
    List<Student> findByCitiesNotIncluded(List<String> cities);


    // =================================================================
    // 2. LOGICAL OPERATORS
    // =================================================================

    // OR Operator ($or)
    @Query("{ '$or' : [ { 'name' : ?0 }, { 'email' : ?1 } ] }")
    List<Student> findByNameOrEmail(String name, String email);

    // AND Operator ($and) - Explicit (Implicit is just comma separated)
    @Query("{ '$and' : [ { 'isActive' : true }, { 'age' : { $gt : 18 } } ] }")
    List<Student> findActiveAdults();

    // NOT Operator ($not)
    @Query("{ 'age' : { $not : { $lt : 18 } } }")
    List<Student> findNotMinors();


    // =================================================================
    // 3. REGEX & TEXT SEARCH
    // =================================================================

    // Regex with Options (Case Insensitive 'i')
    @Query("{ 'name' : { '$regex' : ?0, '$options' : 'i' } }")
    List<Student> findByNameRegex(String regexPattern);

    // Text Search (Requires @TextIndexed on model fields)
    @Query("{ '$text' : { '$search' : ?0 } }")
    List<Student> searchByText(String keyword);


    // =================================================================
    // 4. ARRAY OPERATIONS
    // =================================================================

    // Array Contains All ($all)
    @Query("{ 'subjects' : { '$all' : ?0 } }")
    List<Student> findByAllSubjects(List<String> requiredSubjects);

    // Element Match ($elemMatch) - For checking objects inside a list
    // Matches students who have a score in 'Math' greater than 90
    @Query("{ 'examScores' : { '$elemMatch' : { 'subject' : 'Math', 'marks' : { $gt : 90 } } } }")
    List<Student> findMathToppers();

    // Check Array Size ($size)
    @Query("{ 'subjects' : { '$size' : ?0 } }")
    List<Student> findBySubjectCount(int count);


    // =================================================================
    // 5. NULL & EXISTENCE CHECKS
    // =================================================================

    // Check if field Exists ($exists)
    @Query("{ 'address' : { '$exists' : true } }")
    List<Student> findStudentsWithAddress();

    // Check if field is Null
    @Query("{ 'email' : null }")
    List<Student> findStudentsWithoutEmail();


    // =================================================================
    // 6. PROJECTIONS (Limiting Returned Fields)
    // =================================================================

    // Return only Name and Email (1 = Include, 0 = Exclude)
    @Query(value = "{ 'isActive' : true }", fields = "{ 'name' : 1, 'email' : 1 }")
    List<Student> findAllActiveNamesAndEmails();

    // Return everything EXCEPT Metadata
    @Query(value = "{}", fields = "{ 'metadata' : 0 }")
    List<Student> findAllExcludingMetadata();


    // =================================================================
    // 7. MODIFYING OPERATIONS (@Update)
    // =================================================================

    // $set: Update a specific field
    @Query("{ 'email' : ?0 }")
    @Update("{ '$set' : { 'name' : ?1 } }")
    long updateNameByEmail(String email, String newName);

    // $inc: Increment a numeric value
    @Query("{ '_id' : ?0 }")
    @Update("{ '$inc' : { 'age' : 1 } }")
    void incrementAge(String id);

    // $push: Add item to array
    @Query("{ '_id' : ?0 }")
    @Update("{ '$push' : { 'subjects' : ?1 } }")
    long addSubject(String id, String subject);

    // $pull: Remove item from array
    @Query("{ '_id' : ?0 }")
    @Update("{ '$pull' : { 'subjects' : ?1 } }")
    long removeSubject(String id, String subject);

    // $unset: Remove a field entirely from the document
    @Query("{ '_id' : ?0 }")
    @Update("{ '$unset' : { 'metadata' : 1 } }")
    void deleteMetadata(String id);

    // $currentDate: Set field to now
    @Query("{ '_id' : ?0 }")
    @Update("{ '$currentDate' : { 'updatedAt' : true } }")
    void touchTimestamp(String id);


    // =================================================================
    // 8. META-ANNOTATIONS (@CountQuery, @DeleteQuery, @ExistsQuery)
    // =================================================================

    // Count with custom query (Instead of @Query(count=true))
    @CountQuery("{ 'isActive' : true }")
    long countActiveStudents();

    // Delete with custom query (Instead of @Query(delete=true))
    @DeleteQuery("{ 'isActive' : false }")
    long deleteInactiveStudents();

    // Exists with custom query (Instead of @Query(exists=true))
    @ExistsQuery("{ 'email' : ?0 }")
    boolean checkStudentExists(String email);


    // =================================================================
    // 9. @Query ATTRIBUTE FLAGS
    // =================================================================

    // Delete Flag (Standard way)
    @Query(value = "{ 'age' : { $lt : 18 } }", delete = true)
    List<Student> removeMinorsAndReturnThem(); // Returns deleted docs

    // Count Flag (Standard way)
    @Query(value = "{ 'gpa' : { $gte : 4.0 } }", count = true)
    long countGeniuses();

    // Exists Flag (Standard way)
    @Query(value = "{ 'name' : ?0 }", exists = true)
    boolean doesNameExist(String name);

    // Sort Attribute (Hardcoded Sort)
    @Query(value = "{ 'isActive' : true }", sort = "{ 'age' : -1 }")
    List<Student> findActiveSortedByAge();


}
