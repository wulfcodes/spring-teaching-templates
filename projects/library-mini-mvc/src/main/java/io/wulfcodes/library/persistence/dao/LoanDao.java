package io.wulfcodes.library.persistence.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import io.wulfcodes.library.model.po.Loan;


@Repository
public class LoanDao {

    private final RowMapper<Loan> loanRowMapper = (resultSet, rowNumber) -> new Loan(
        resultSet.getLong("id"),
        resultSet.getLong("user_id"),
        resultSet.getLong("book_id"),
        resultSet.getDate("issue_date").toLocalDate(),
        resultSet.getDate("due_date").toLocalDate()
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public int addLoan(Loan loan) {
        String query = """
            INSERT INTO loans(user_id, book_id, due_date)
            VALUES (?, ?, ?);
            """;

        return jdbcTemplate.update(
            query,
            loan.getUserId(),
            loan.getBookId(),
            loan.getDueDate()
        );
    }

    public boolean checkLoan(Long userId, Long bookId) {
        String query = "SELECT EXISTS(SELECT 1 FROM loans WHERE user_id = ? AND book_id = ?);";
        return jdbcTemplate.queryForObject(
            query,
            Boolean.class,
            userId, bookId
        );
    }

    public List<Loan> getIssuedBooks() {
        String query = "SELECT * FROM loans;";
        return jdbcTemplate.query(query, loanRowMapper);
    }

    public List<Loan> getIssuedBooksByUserId(Long userId) {
        String query = "SELECT * FROM loans WHERE user_id = ?;";
        return jdbcTemplate.query(query, loanRowMapper, userId);
    }

    public long countIssuedBooks() {
        String query = "SELECT COUNT(*) FROM loans;";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public long countDueBooks() {
        String query = "SELECT COUNT(*) FROM loans WHERE due_date < CURRENT_DATE;";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public void removeLoanById(Long loanId) {
        String query = "DELETE FROM loans WHERE loan_id = ?;";
        jdbcTemplate.update(query, loanId);
    }

}
