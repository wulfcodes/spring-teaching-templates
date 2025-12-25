package io.wulfcodes.mvc.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;
import io.wulfcodes.mvc.model.Product;

@Controller
//@RequestMapping("/web")
public class OutputController {

    /* View Types */

    // Mustache View
    @GetMapping("/profile")
    public ModelAndView profilePage(
        @RequestParam("name")
        String name,
        @RequestParam("username")
        String username,
        @RequestParam("email")
        String email,
        @RequestParam(name = "bio", required = false)
        String bio
    ) {
        ModelAndView view = new ModelAndView("profile");
        view.addObject("name", name);
        view.addObject("username", username);
        view.addObject("email", email);
        view.addObject("bio", bio);
        return view;

        // default 200
    }

    // JSP View
    @PostMapping(path = "/confirm-product", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView productPage(@ModelAttribute Product product) {
        ModelAndView view = new ModelAndView("confirm-product");
        view.addObject("productName", product.getProductName());
        view.addObject("category", product.getCategory());
        view.addObject("price", product.getPrice());
        view.addObject("discount", product.getDiscount());
        view.addObject("inStock", product.getInStock());
        return view;
    }

    @GetMapping("/home")
    public InternalResourceView home() {
        return new InternalResourceView("/homepage.html");
    }

    @GetMapping("/social")
    public RedirectView social() {
        return new RedirectView("https://www.facebook.com/");
        // default 302, ie client should use /social endpoint, even though resource is different
        // makes sense, cause social handle might change later
    }

    /* Manipulating Status Code */

    @PostMapping("/add-product")
    public ModelAndView addProduct(@ModelAttribute Product product) {
        System.out.println("Adding Product " + product);

        ModelAndView modelAndView = new ModelAndView("new-product");
        modelAndView.setStatus(HttpStatus.CREATED); // 201 indicates resource is created
        return modelAndView;
    }

    @GetMapping("/similar-product")
    public RedirectView similarProduct(@RequestParam("product") String product) {
        RedirectView redirectView = new RedirectView("https://www.google.com/search?q=" + product);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // 301 indicates use, google directly instead
        return redirectView;
    }

    /* Implicit View Resolution */

    @GetMapping("/demo1")
    public String printName() {
        return "demo";
    }

    @GetMapping("/demo2")
    public String viewName() {
        return "forward:/demo1";     // same as InternalResourceView
    }

    @GetMapping("/demo3")
    public String showName() {
        return "redirect:/demo1";   // same as RedirectView
    }

    /* Implicit View Resolution -> Status Code */

    @PostMapping("/perform-task")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String performTask() {
        System.out.println("Task is accepted, returning to homepage");
        return "index";
    }

    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
    public String welcome(@RequestParam(name = "lang", required = false) String lang) {
        if (Objects.isNull("lang"))
            return "redirect:/welcome.html";
        return "redirect:/welcome-" + lang + ".html";
    }

    /* Implicit View Resolution -> Working with Models */

    @GetMapping("/anon")
    public String getUser(
        Model model
    ) {
        model.addAttribute("name", "Anonymous");
        model.addAttribute("email", "anon@hotmail.com");
        return "profile";
    }

    @ModelAttribute("bio")
    public String bio() {
        return "This is default bio added in all Model attribute, if not present";
    }

    @ModelAttribute("username")
    public String username() {
        return "AnonUser";
    }

    @GetMapping("/preferences")
    public String preferences(
        @RequestParam(name = "username", required = false)
        String username,
        @RequestParam(defaultValue = "false")
        boolean preference1,
        @RequestParam(defaultValue = "false")
        boolean preference2,
        Model model
    ) {
        if (username != null)
            model.addAttribute("username", username);
        if (preference1)
            model.addAttribute("preference1", preference1);
        if (preference2)
            model.addAttribute("preference2", preference2);

        return "preferences";
    }

    @GetMapping("/login-redirect")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String loginRedirect(
        @RequestParam(defaultValue = "false")
        boolean preference1,
        @RequestParam(defaultValue = "false")
        boolean preference2,
        RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("preference1", preference1);
        redirectAttributes.addFlashAttribute("preference2", preference2);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping(path = "/text", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String textContent(@RequestParam(name = "name", required = false) String name) {
        if (name == null)
            throw new IllegalArgumentException("Name is not provided");
        return "Hey there %s, This is a sample text".formatted(name);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

}
