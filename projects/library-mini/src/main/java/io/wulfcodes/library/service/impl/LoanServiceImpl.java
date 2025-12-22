package io.wulfcodes.library.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.exception.LoanDuplicationException;
import io.wulfcodes.library.exception.LoanPeriodException;
import io.wulfcodes.library.mapper.LoanMapper;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.dto.LoanData;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.model.po.Loan;
import io.wulfcodes.library.persistence.dao.LoanDao;
import io.wulfcodes.library.service.spec.BookService;
import io.wulfcodes.library.service.spec.LoanService;
import io.wulfcodes.library.service.spec.UserService;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    public void issueBook(Long userId, Long bookId, LocalDate dueDate) {
        if (loanDao.checkLoan(userId, bookId))
            throw new LoanDuplicationException("This book is already loaned to the user.");
        else if (!dueDate.isAfter(LocalDate.now()))
            throw new LoanPeriodException("Invalid due date.");

        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setBookId(bookId);
        loan.setDueDate(dueDate);

        bookService.decreaseAvailableBooks(bookId);

        loanDao.addLoan(loan);
    }

    @Override
    public void returnBook(Long loanId, Long bookId) {
        bookService.increaseAvailableBooks(bookId);
        loanDao.removeLoanById(loanId);
    }

    @Override
    public List<LoanData> getAllIssuedBooks(boolean detailed) {
        List<Loan> loans = loanDao.getIssuedBooks();

        if (detailed)
            return loans.stream()
                        .map(loan -> {
                            UserData userData = userService.getUserById(loan.getUserId());
                            BookData bookData = bookService.getBookById(loan.getBookId());
                            return loanMapper.toData(loan, userData, bookData);
                        })
                        .toList();
        else
            return loanMapper.toDataList(loans);
    }

    @Override
    public List<LoanData> getIssuedBooksByUserId(Long userId) {
        UserData userData = userService.getUserById(userId);
        List<Loan> loans = loanDao.getIssuedBooksByUserId(userId);
        return loans.stream()
                    .map(loan -> {
                        BookData bookData = bookService.getBookById(loan.getBookId());
                        return loanMapper.toData(loan, userData, bookData);
                    })
                    .toList();
    }

    @Override
    @Async
    public CompletableFuture<Long> getIssuedBooksCountAsync() {
        return CompletableFuture.completedFuture(loanDao.countIssuedBooks());
    }

    @Override
    @Async
    public CompletableFuture<Long> getDueBooksCountAsync() {
        return CompletableFuture.completedFuture(loanDao.countDueBooks());
    }
}
