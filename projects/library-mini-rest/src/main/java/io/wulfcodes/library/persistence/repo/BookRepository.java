package io.wulfcodes.library.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.wulfcodes.library.model.po.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
