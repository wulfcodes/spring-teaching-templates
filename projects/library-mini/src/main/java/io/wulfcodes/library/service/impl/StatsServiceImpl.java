package io.wulfcodes.library.service.impl;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.model.vo.Stats;
import io.wulfcodes.library.service.spec.BookService;
import io.wulfcodes.library.service.spec.LoanService;
import io.wulfcodes.library.service.spec.StatsService;
import io.wulfcodes.library.service.spec.UserService;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;


    @Override
    public Stats getStats() {
        CompletableFuture<Long> usersFuture = userService.getUsersCountAsync();
        CompletableFuture<Long> booksFuture = bookService.getBooksCountAsync();
        CompletableFuture<Long> issuedBooksFuture = loanService.getIssuedBooksCountAsync();
        CompletableFuture<Long> dueBooksFuture = loanService.getDueBooksCountAsync();

        return new Stats(booksFuture.join(),usersFuture.join(),issuedBooksFuture.join(),dueBooksFuture.join());
    }
}
