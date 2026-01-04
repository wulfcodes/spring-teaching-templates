package io.wulfcodes.secc.model;

import java.time.LocalDate;

public class Todo {
    private Long id;
    private String username;
    private String title;
    private Boolean isCompleted;
    private LocalDate dateTargeted;

    public Todo() {}

    public Todo(Long id, String username, String title, Boolean isCompleted, LocalDate dateTargeted) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.isCompleted = isCompleted;
        this.dateTargeted = dateTargeted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDate getDateTargeted() {
        return dateTargeted;
    }

    public void setDateTargeted(LocalDate dateTargeted) {
        this.dateTargeted = dateTargeted;
    }

    @Override
    public String toString() {
        return "Todo{" +
            "id=" + id +
            ", userId=" + username +
            ", title='" + title + '\'' +
            ", completed=" + isCompleted +
            ", targetedDate=" + dateTargeted +
            '}';
    }
}

