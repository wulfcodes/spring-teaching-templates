package io.wulfcodes.library.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import io.wulfcodes.library.exception.UserNotFoundException;
import io.wulfcodes.library.model.ro.AuthData;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.service.spec.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/sign-up")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/sign-up")
    public String registerUser() {
        return null;
    }

    @GetMapping("/sign-in")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/sign-in")
    public String loginUser(
        @ModelAttribute
        AuthData authData,
        HttpSession httpSession,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        UserData userData = (UserData)httpSession.getAttribute("userData");

        if (userData == null) {
            userData = authService.loginUser(authData.email(), authData.password());
            httpSession.setAttribute("userData", userData);
        }


        redirectAttributes.addFlashAttribute("user", userData);
        return "redirect:/dashboard";
    }

    @PostMapping("/sign-out")
    public String logoutUser(HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "login";
    }

}
