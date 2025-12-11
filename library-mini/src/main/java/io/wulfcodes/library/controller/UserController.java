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
import io.wulfcodes.library.service.spec.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("year")
    public Integer year() {
        return LocalDate.now().getYear();
    }

    @ModelAttribute("user")
    public UserData user(HttpSession httpSession) {
        return (UserData) httpSession.getAttribute("userData");
    }

    @GetMapping("/list")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "view-users";
    }

}
