package io.wulfcodes.library.controller;

import java.time.LocalDate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.service.spec.LoanService;
import io.wulfcodes.library.service.spec.StatsService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private LoanService loanService;

    @ModelAttribute("year")
    public Integer year() {
        return LocalDate.now().getYear();
    }

    @ModelAttribute("user")
    public UserData user(HttpSession httpSession) {
        return (UserData) httpSession.getAttribute("userData");
    }

    @GetMapping
    public String dashboardPage(
        @ModelAttribute("user")
        UserData userData,
        Model model
    ) {
        model.addAttribute("loans", loanService.getAllIssuedBooks(true));
        model.addAttribute("stats", statsService.getStats());
        return "dashboard";
    }

}
