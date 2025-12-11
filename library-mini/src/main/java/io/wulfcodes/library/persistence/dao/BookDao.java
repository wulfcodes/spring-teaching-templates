package io.wulfcodes.library.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import io.wulfcodes.library.model.po.Book;
import io.wulfcodes.library.persistence.repo.BookRepository;

@Repository
public class BookDao {

    private final RowMapper<Book> bookRowMapper = (resultSet, rowNumber) -> new Book(
        resultSet.getLong("id"),
        resultSet.getString("title"),
        resultSet.getString("author"),
        resultSet.getString("category"),
        resultSet.getString("isbn"),
        resultSet.getString("publisher"),
        resultSet.getInt("total_copies"),
        resultSet.getInt("available_copies"),
        resultSet.getBoolean("is_available")
    );

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        String query = "SELECT * FROM books WHERE available_copies > 0 AND is_available = true";

        return jdbcClient.sql(query)
                         .query(bookRowMapper)
                         .list();
    }

    @Transactional
    public int updateAvailableCopies(Long bookId, int delta) {
        String query = """
                UPDATE books
                SET available_copies = available_copies + :delta
                WHERE id = :id
            """;

        return jdbcClient.sql(query)
                         .param("id", bookId)
                         .param("delta", delta)
                         .update();
    }

    public long getBooksCount() {
        return bookRepository.count();
    }


}
