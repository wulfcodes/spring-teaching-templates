package io.wulfcodes.library.controller.resource.v1;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.coyote.Response;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.dto.Message;
import io.wulfcodes.library.model.po.Book;
import io.wulfcodes.library.service.spec.BookService;

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookData> addBook(@RequestBody BookData book) {
        long bookId = bookService.createBook(book);
        book.setId(bookId);

        ResponseEntity<BookData> response = ResponseEntity.status(HttpStatus.CREATED)
                                                          .header(HttpHeaders.LOCATION, "/v1/books/".concat(String.valueOf(bookId)))
                                                          .body(book);
        return response;
    }

    @GetMapping("/:bookId")
    public ResponseEntity<?> fetchBooks(@PathVariable Long bookId) {
        Optional<BookData> bookData = bookService.getBookById(bookId);
        if (bookData.isPresent()) {
            BookData book = bookData.get();

            ResponseEntity<BookData> response = ResponseEntity.status(HttpStatus.OK)
                                                              .body(book);
            return response;
        } else {
            ResponseEntity<Message> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                             .body(new Message(String.format("Book with id %d not found", bookId)));
            return response;
        }
    }

    @GetMapping
    public ResponseEntity<List<BookData>> fetchBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(bookService.getAllBooks());
    }

    @PutMapping("/:bookId")
    public ResponseEntity<Void> updateBook(@PathVariable Long bookId, @RequestBody BookData book) {
        if (bookService.updateBook(bookId, book)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/:bookId")
    public ResponseEntity<BookData> modifyBook(@PathVariable Long bookId, @RequestBody BookData book) {

    }

    @DeleteMapping("/:bookId")
    public ResponseEntity<BookData> deleteBook(@PathVariable Long bookId) {

    }

}
