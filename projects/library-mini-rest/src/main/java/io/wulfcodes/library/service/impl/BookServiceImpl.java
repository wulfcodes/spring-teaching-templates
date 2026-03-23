package io.wulfcodes.library.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.persistence.dao.BookDao;
import io.wulfcodes.library.service.spec.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public long createBook(BookData bookData) {
        return 0;
    }

    @Override
    public Optional<BookData> getBookById(long id) {
        return Optional.empty();
    }

    @Override
    public List<BookData> getAllBooks() {
        return List.of();
    }

    @Override
    public boolean updateBook(long id, BookData bookData) {
        return false;
    }

    @Override
    public boolean modifyBook(long id, BookData bookData) {
        return false;
    }

    @Override
    public boolean deleteBookById(long id) {
        return false;
    }
}
