package ru.thomaskohouse.ArticleManager.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.util.List;

/**
 * Контроллер для управления юзерами:
 * создание, просмотр профиля
 */
@Controller
public class UserController {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createUserForm(Model model){
        logger.info("Web Request GET /user/new");
        model.addAttribute("user", new UserDto());
        model.addAttribute("isNewUser", true);
        model.addAttribute("isAdmin", false);
        return "userEdit";
    }

    @GetMapping("/user/{userId}/edit")
    public String editUser(Model model, @PathVariable Long userId){
        logger.info("Web Request GET /user/{}/edit", userId);
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("isNewUser", false);
        return "userEdit";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        logger.info("Web Request GET /user/{}", id);
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
        logger.info("Web Request /user/profile");
        return "redirect:/user/" + userService.getCurrentUserDto().getId();
    }

    @PostMapping("/user/new")
    public String createNewUser(@ModelAttribute UserDto user, @RequestParam String password,
                                @RequestParam String checkboxAdmin, Model model){
        boolean isAdmin = checkboxAdmin.equals("on");
        user.setRole(isAdmin ? "ADMIN" : "USER");
        logger.info("Web Request POST /user/new, new user: {}", user);
        model.addAttribute("user", userService.addUser(user, password));
        return "userView";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user){
        logger.info("Web Request POST /user/{update}, updated user: {}", user);
        user = userService.updateUser(id, user);
        return "redirect:/user/" + user.getId();
    }


}
