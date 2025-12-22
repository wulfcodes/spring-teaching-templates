package io.wulfcodes.library.model.dto;

import java.time.LocalDate;

public class LoanData {
    private Long loanId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Long userId;
    private String fullName;
    private String email;
    private Long bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String status;

    public boolean isOk() {
        return "ok".equalsIgnoreCase(status);
    }

    public boolean isLastday() {
        return "lastday".equalsIgnoreCase(status);
    }

    public boolean isOverdue() {
        return "overdue".equalsIgnoreCase(status);
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "LoanData{" +
            "loanId=" + loanId +
            ", issueDate=" + issueDate +
            ", dueDate=" + dueDate +
            ", userId=" + userId +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", bookId='" + bookId + '\'' +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", category='" + category + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
