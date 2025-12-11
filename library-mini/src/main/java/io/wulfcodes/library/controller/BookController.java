package io.wulfcodes.library.controller;

import java.time.LocalDate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.service.spec.BookService;
import io.wulfcodes.library.service.spec.LoanService;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @ModelAttribute("year")
    public Integer year() {
        return LocalDate.now().getYear();
    }

    @GetMapping("/issue")
    public String issuePage(Model model) {
        model.addAttribute("books", bookService.getAvailableBooks());
        return "issue-book";
    }

    @ModelAttribute("user")
    public UserData user(HttpSession httpSession) {
        return (UserData) httpSession.getAttribute("userData");
    }

    @PostMapping("/issue")
    public String issueBook(
        @RequestParam
        Long bookId,
        @RequestParam
        LocalDate dueDate,
        @ModelAttribute("user")
        UserData userData,
        HttpSession httpSession,
        RedirectAttributes redirectAttributes,
        HttpServletResponse httpServletResponse
    ) {
        try {
            Long userId = userData.getUserId();
            loanService.issueBook(userId, bookId, dueDate);
            redirectAttributes.addFlashAttribute("successMessage", "Book issued successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "redirect:/book/issue";
    }

    @GetMapping("/list")
    public String booksPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "view-books";
    }

    @GetMapping("/return")
    public String returnPage(@ModelAttribute("user") UserData userData, Model model) {
        model.addAttribute("loans", loanService.getIssuedBooksByUserId(userData.getUserId()));
        return "return-book";
    }

}
