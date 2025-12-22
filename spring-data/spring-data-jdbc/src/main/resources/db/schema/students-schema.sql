-- This drops the table if it already exists, so we can re-run the app
DROP TABLE IF EXISTS students;

-- Create the student table
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT,
    percentage FLOAT,
    is_enrolled BOOLEAN DEFAULT false
);