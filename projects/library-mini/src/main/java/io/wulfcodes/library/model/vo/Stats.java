package io.wulfcodes.library.model.vo;

public class Stats {
    private final long totalBooks;
    private final long totalUsers;
    private final long booksIssued;
    private final long booksDue;

    public Stats(long totalBooks, long totalUsers, long booksIssued, long booksDue) {
        this.totalBooks = totalBooks;
        this.totalUsers = totalUsers;
        this.booksIssued = booksIssued;
        this.booksDue = booksDue;
    }
}
