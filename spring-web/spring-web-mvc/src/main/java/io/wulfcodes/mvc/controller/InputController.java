package io.wulfcodes.mvc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import io.wulfcodes.mvc.model.ContactForm;

@Controller
public class InputController {

    @RequestMapping("/hello1")
    public void sayHello1() {
        System.out.println("Hello World from 1");
    }

    @RequestMapping(path = "/hello2", method = {RequestMethod.GET, RequestMethod.POST})
    public void sayHello2() {
        System.out.println("Hello World from 2, only GET and POST methods allowed");
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public void search(@RequestParam("query") String query, @RequestParam("hashtag") String hashtag) {
        System.out.println("""
            Searching
            Query: %s
            Hashtag: %s
            """.formatted(query, hashtag));
    }

    @RequestMapping(path = "/contact1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void contactUs1(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("message") String message) {
        System.out.println("""
            User Contacted First
            Name: %s
            Email: %s
            Message: %s
            """.formatted(name, email, message));
    }

    @RequestMapping(path = "/contact2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void contactUs2(
        @ModelAttribute
        ContactForm contactForm,
        @RequestHeader(HttpHeaders.USER_AGENT)
        String userAgentHeader,
        @CookieValue("JSESSIONID")
        String sessionId
    ) {
        System.out.println("""
            User Contacted Second from %s
            Name: %s
            Email: %s
            Message: %s,
            SessionId: %s
            """.formatted(userAgentHeader, contactForm.getName(), contactForm.getEmail(), contactForm.getMessage(), sessionId));
    }

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void handleUpload(
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestPart("profilePic") MultipartFile profilePic
    ) throws IOException {
        System.out.println("""
            User is uploading file: 
            Username: %s
            Email: %s
            """.formatted(profilePic.getOriginalFilename(), username, email));

        // Creating directory to save uploaded files, can be any directory with simple logic
        Path projectRoot = Paths.get("").toAbsolutePath();
        Path uploadsDir = projectRoot.resolve("src/main/resources/uploads");
        Files.createDirectories(uploadsDir);
        Path filePath = uploadsDir.resolve(profilePic.getOriginalFilename());


        // saving the uploaded file
        profilePic.transferTo(filePath.toFile());
        System.out.println("Saved file to: " + filePath);
    }


}
