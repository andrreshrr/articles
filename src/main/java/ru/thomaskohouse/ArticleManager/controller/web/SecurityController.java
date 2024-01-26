package ru.thomaskohouse.ArticleManager.controller.web;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;


/**
 * Контроллер для управления процессами аутенфикации, регистрации, выхода из профиля и ошибки при логине
 */
@Controller
public class SecurityController {
    Logger logger = LoggerFactory.getLogger(SecurityController.class);
    @GetMapping("/login")
    String login() {
        logger.info("Web Request /login");
        return "login";
    }


    @GetMapping("/logout-miss")
    String logout(Model model){

        logger.info("Web Request /logout-miss");
        model.addAttribute("logout", "true");
        return "login";
    }

    @GetMapping("/login-error")
    String loginError(Model model){

        logger.info("Web Request /login-error");
        model.addAttribute("error", "true");
        return "login";
    }


}
