package ru.thomaskohouse.ArticleManager.controller.web;

import org.hibernate.dialect.SybaseSqlAstTranslator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Контроллер для управления процессами аутенфикации, регистрации, выхода из профиля и ошибки при логине
 */
@Controller
public class SecurityController {

    @GetMapping("/login")
    String login() {
        return "login";
    }


    @GetMapping("/logout-miss")
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
