package io.wulfcodes.filinc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        System.out.println("Step 3: operating on request test1");

        // service layer

        System.out.println("Step 4: generating a response test1");
        return "test1";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2() {
        System.out.println("Step 3: operating on request test2");

        // service layer

        System.out.println("Step 4: generating a response test2");
        return "test2";
    }

}
