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
}
