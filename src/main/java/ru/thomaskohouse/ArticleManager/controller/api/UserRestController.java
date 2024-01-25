package ru.thomaskohouse.ArticleManager.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;
import ru.thomaskohouse.ArticleManager.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping("/user/new")
    public UserDto createUser(@RequestBody UserDto user, @RequestParam String password){
        return userService.addUser(user, password);
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUserDto(id);
    }

}
