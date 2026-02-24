package io.wulfcodes.library.model.po;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 3322224001446408085L;

    @Column("total_copies")
    private int totalCopies;
    @Column("available_copies")
    private int availableCopies;
    @Column("is_available")
    private boolean isAvailable;

    @Id
    private Long id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String publisher;

    public Book() {}

    @PersistenceCreator
    public Book(Long id, String title, String author, String category, String isbn, String publisher, int totalCopies, int availableCopies, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publisher = publisher;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.isAvailable = isAvailable;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean isAvailable() {
        return isAvailable && availableCopies > 0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
            "totalCopies=" + totalCopies +
            ", availableCopies=" + availableCopies +
            ", available=" + isAvailable +
            ", id=" + id +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", category='" + category + '\'' +
            ", isbn='" + isbn + '\'' +
            ", publisher='" + publisher + '\'' +
            '}';
    }
}
