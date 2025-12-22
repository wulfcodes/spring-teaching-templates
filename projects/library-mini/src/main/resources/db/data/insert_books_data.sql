INSERT IGNORE INTO books
    (title, author, category, isbn, publisher, total_copies, available_copies, is_available)
VALUES
    ('Clean Code', 'Robert C. Martin', 'Technology', '978-0132350884', 'Prentice Hall', 5, 5, true),
    ('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '978-0743273565', 'Scribner', 3, 1, true),
    ('A Brief History of Time', 'Stephen Hawking', 'Science', '978-0553380163', 'Bantam', 2, 0, false),
    ('Harry Potter and the Sorcerers Stone', 'J.K. Rowling', 'Fantasy', '978-0590353427', 'Scholastic', 10, 8, true),
    ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'History', '978-0062316097', 'Harper', 4, 4, true);