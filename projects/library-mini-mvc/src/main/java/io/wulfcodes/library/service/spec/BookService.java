package io.wulfcodes.library.service.spec;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import io.wulfcodes.library.model.dto.BookData;

public interface BookService {
    BookData getBookById(Long bookId);

    List<BookData> getAllBooks();

    List<BookData> getAvailableBooks();

    void increaseAvailableBooks(Long bookId);

    void decreaseAvailableBooks(Long bookId);

    CompletableFuture<Long> getBooksCountAsync();

    void saveBook(BookData bookData);

    void updateBook(BookData bookData);

    void deleteBook(Long bookId);
}
