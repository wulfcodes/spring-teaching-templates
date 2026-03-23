package io.wulfcodes.library.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.po.Book;
import io.wulfcodes.library.persistence.repo.BookRepository;

@Repository
public class BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Optional<Book> insertBook(Book book) {
        return Optional.of(bookRepository.save(book));
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> updateBook(Long id, Book book) {

    }



}
