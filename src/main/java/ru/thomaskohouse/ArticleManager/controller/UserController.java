package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/user/new")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/user/new")
    public User createUserForm(@ModelAttribute User user, String password){
        user.setPassword(UUID.randomUUID());
        return userService.addUser(user);
    }
}
