package io.wulfcodes.library.model.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(length = 100)
    private String category;

    @Column(unique = true, length = 20)
    private String isbn;

    private String publisher;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Book() {}

    public Book(String title, String author, String category, String isbn, String publisher, Integer totalCopies, Integer availableCopies, Boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publisher = publisher;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.isAvailable = isAvailable;
    }

    public Book(Long id, String title, String author, String category, String isbn, String publisher, Integer totalCopies, Integer availableCopies, Boolean isAvailable) {
        this(title, author, category, isbn, publisher, totalCopies, availableCopies, isAvailable);
        this.id = id;
    }

    @PrePersist
    private void atSave() {
        if (totalCopies == null)
            totalCopies = 1;
        if (availableCopies == null)
            availableCopies = 1;
        this.isAvailable = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
