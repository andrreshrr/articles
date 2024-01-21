package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.util.List;

/**
 * Контроллер для управления юзерами:
 * создание, просмотр профиля
 */
@Controller
public class UserController {
    @Autowired
    final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        User user = userService.getUser(id);
        List<Article> userArticles = userService.getUserArticles(user);
        model.addAttribute("user", user);
        model.addAttribute("haveArticles", (userArticles != null) && (!userArticles.isEmpty()));
        model.addAttribute("recentArticles", userArticles);

        return "userView";
    }

    @GetMapping("/user/profile")
    public String viewCurrentUser(){
        return "redirect:/user/" + userService.getCurrentUser().getId();
    }

    @PostMapping("/user/new")
    public String createNewUser(@ModelAttribute User user){
        user = userService.addUser(user);
        return "userView";
    }

}
