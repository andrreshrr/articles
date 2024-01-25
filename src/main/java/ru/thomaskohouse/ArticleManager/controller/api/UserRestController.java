package ru.thomaskohouse.ArticleManager.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.service.UserService;

@RestController
@RequestMapping("/api")
@Tag(name = "Контроллер пользователей", description = "Позволяет манипулировать пользователями")
public class UserRestController {

    @Autowired
    UserService userService;

    @Operation(
            summary = "Создание нового пользователя",
            description = "Позволяет создать нового пользователя"
    )
    @PostMapping("/user/new")
    public UserDto createUser(@RequestBody UserDto user, @RequestParam String password){
        return userService.addUser(user, password);
    }


    @Operation(
            summary = "Получить пользователя",
            description = "Позволяет получить информацию о конкретном пользователе по его id"
    )
    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

}
