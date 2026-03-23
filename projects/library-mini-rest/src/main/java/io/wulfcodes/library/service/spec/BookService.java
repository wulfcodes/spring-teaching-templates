package io.wulfcodes.library.service.spec;

import java.util.List;
import java.util.Optional;
import io.wulfcodes.library.model.dto.BookData;

public interface BookService {

    long createBook(BookData bookData);
    Optional<BookData> getBookById(long id);
    List<BookData> getAllBooks();
    boolean updateBook(long id, BookData bookData);
    boolean modifyBook(long id, BookData bookData);
    boolean deleteBookById(long id);

}
