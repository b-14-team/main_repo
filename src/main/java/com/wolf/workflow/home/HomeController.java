package com.wolf.workflow.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/")
    public String index() {
        return "board";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";

    }
}

