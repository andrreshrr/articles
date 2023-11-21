package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @Controller
    class LoginController {
        @GetMapping("/login")
        String login() {
            return "login";
        }
    }


}
