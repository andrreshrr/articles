package ru.thomaskohouse.ArticleManager.controller;

import org.hibernate.dialect.SybaseSqlAstTranslator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    @Controller
    class LoginController {
        @GetMapping("/login")
        String login() {
            return "login";
        }
        @GetMapping("/logout")
        String logout(Model model){
            model.addAttribute("logout", "true");
            return "login";

        }

        @GetMapping("/login-error")
        String loginError(Model model){
            model.addAttribute("error", "true");
            return "login";

        }
    }


}
