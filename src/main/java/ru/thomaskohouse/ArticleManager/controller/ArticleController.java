package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.ArticleManagerService;

import java.time.LocalDateTime;


/**
 * Контроллер для манипуляции одной статьей.
 * Просмотр, создание
 */
@Controller
public class ArticleController {
    @Autowired
    ArticleManagerService service;

    @GetMapping("/article/{id}")
    public String articlePage(@PathVariable Long id, Model model){
        Article article = service.getArticle(id);
        User author = article.getAuthor();
        model.addAttribute("article", article);
        model.addAttribute("author", author);
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
        User user = service.getUser(1L);
        article.setAuthor(user);
        Article newArticle = service.addArticle(article);
        return "redirect:/article/"+newArticle.getId();
    }
}
