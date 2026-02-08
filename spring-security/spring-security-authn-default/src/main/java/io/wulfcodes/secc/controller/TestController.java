package io.wulfcodes.secc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test-1")
    @ResponseBody
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public String test(@AuthenticationPrincipal String userIdentifier){
        return "Hello Basic User, %s".formatted(userIdentifier);
    }

    @GetMapping("/test-2")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public String test2(@AuthenticationPrincipal String userIdentifier){
        return "Hello Admin User, %s".formatted(userIdentifier);
    }

}
