package ru.thomaskohouse.ArticleManager.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;
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
        model.addAttribute("user", new UserDto());
        model.addAttribute("isNewUser", true);
        return "userEdit";
    }

    @GetMapping("/user/{userId}/edit")
    public String editUser(Model model, @PathVariable Long userId){
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("isNewUser", false);
        return "userEdit";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        UserDto user = userService.getUser(id);
        List<ArticleDto> userArticles = userService.getUserArticles(user);
        boolean isCurrentUser = user.equals(userService.getCurrentUserDto());
        model.addAttribute("user", user);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("haveArticles", (userArticles != null) && (!userArticles.isEmpty()));
        model.addAttribute("recentArticles", userArticles);

        return "userView";
    }

    @GetMapping("/user/profile")
    public String viewCurrentUser(){
        return "redirect:/user/" + userService.getCurrentUserDto().getId();
    }

    @PostMapping("/user/new")
    public String createNewUser(@ModelAttribute UserDto user, @RequestParam String password, Model model){
        model.addAttribute("user", userService.addUser(user, password));
        return "userView";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user){
        user = userService.updateUser(id, user);
        return "redirect:/user/" + user.getId();
    }


}
