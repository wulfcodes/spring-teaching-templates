package io.wulfcodes.library.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.exception.BookNotFoundException;
import io.wulfcodes.library.mapper.BookMapper;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.persistence.dao.BookDao;
import io.wulfcodes.library.service.spec.BookService;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookData getBookById(Long bookId) {
        return bookDao.findById(bookId)
                      .map(bookMapper::toData)
                      .orElseThrow(() -> new BookNotFoundException("Book not found."));
    }

    @Override
    public List<BookData> getAllBooks() {
        return bookMapper.toDataList(bookDao.getAllBooks());
    }

    @Override
    public List<BookData> getAvailableBooks() {
        return bookMapper.toDataList(bookDao.getAvailableBooks());
    }

    @Override
    public void increaseAvailableBooks(Long bookId) {
        updateAvailableBooks(bookId, 1);
    }

    @Override
    public void decreaseAvailableBooks(Long bookId) {
        updateAvailableBooks(bookId, -1);
    }

    private void updateAvailableBooks(Long bookId, int change) {
        bookDao.updateAvailableCopies(bookId, change);
    }

    @Override
    @Async
    public CompletableFuture<Long> getBooksCountAsync() {
        return CompletableFuture.completedFuture(bookDao.getBooksCount());
    }


}
