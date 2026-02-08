package io.wulfcodes.secc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test(@AuthenticationPrincipal String userIdentifier){
        return "Hello Authenticated User, %s".formatted(userIdentifier);
    }

}
