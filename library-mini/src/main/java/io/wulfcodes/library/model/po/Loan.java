package io.wulfcodes.library.model.po;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 5017637609843566102L;

    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Loan() {}

    public Loan(Long id, Long userId, Long bookId, LocalDate issueDate, LocalDate dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
