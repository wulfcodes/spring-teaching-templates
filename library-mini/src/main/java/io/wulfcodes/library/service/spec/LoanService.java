package io.wulfcodes.library.service.spec;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import io.wulfcodes.library.model.dto.LoanData;

public interface LoanService {
    void issueBook(Long userId, Long bookId, LocalDate dueDate);
    void returnBook(Long loanId, Long bookId);
    List<LoanData> getAllIssuedBooks(boolean detailed);
    List<LoanData> getIssuedBooksByUserId(Long userId);
    CompletableFuture<Long> getIssuedBooksCountAsync();
    CompletableFuture<Long> getDueBooksCountAsync();
}
