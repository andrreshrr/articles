package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.ArticleManagerService;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.time.LocalDateTime;


/**
 * Контроллер для манипуляции одной статьей.
 * Просмотр, создание
 */
@Controller
public class ArticleController {
    @Autowired
    ArticleManagerService articleManagerService;
    @Autowired
    UserService userService;

    @GetMapping("/article/{id}")
    public String articlePage(@PathVariable Long id, Model model){
        Article article = articleManagerService.getArticle(id); //загружаем тело статьи
        User author = article.getAuthor();                      //загружаем автора
        Comment newComment = new Comment();                     //готовим метаданные комментария
        User currentUser = userService.getCurrentUser();
        newComment.setAuthor(currentUser);
        newComment.setArticle(article);
        boolean isCurrentUser = author.equals(currentUser);


        model.addAttribute("article", article);
        model.addAttribute("author", author);
        model.addAttribute("newComment", newComment);
        model.addAttribute("isCurrentUser", isCurrentUser);

        return "articleView";
    }

    @GetMapping("/article/new")
    public String createArticleForm(Model model){
        model.addAttribute("article", new Article());
        return "articleCreate";
    }

    @PostMapping("/article/new")
    public String createArticle(Model model, @ModelAttribute Article article){
        article.setCreationDateTime(LocalDateTime.now());
        User user = userService.getCurrentUser();
        article.setAuthor(user);
        Article newArticle = articleManagerService.addArticle(article);
        return "redirect:/article/"+newArticle.getId();
    }

    @PostMapping("/comment/new")
    public String addComment(@ModelAttribute Comment newComment) {
        return "redirect:/article/"+ articleManagerService.addComment(newComment).getId();
    }


}
