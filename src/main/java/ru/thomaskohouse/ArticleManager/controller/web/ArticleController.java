package ru.thomaskohouse.ArticleManager.controller.web;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.CommentDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.service.ArticleService;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.time.LocalDateTime;


/**
 * Контроллер для манипуляции одной статьей.
 * CRUD, добавление и удаление комментариев
 */
@Hidden
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable Long id, Model model){
        logger.info("Web Request to /article/"+id);
        ArticleDto article = articleService.getArticle(id);      //загружаем тело статьи
        UserDto author = article.getAuthor();                       //загружаем автора
        CommentDto newComment = new CommentDto();                   //готовим метаданные комментария
        UserDto currentUser = userService.getCurrentUserDto();
        boolean isAdmin = userService.isCurrentUserAdmin();
        model.addAttribute("article", article);
        model.addAttribute("author", author);
        model.addAttribute("newComment", newComment);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isAdmin", isAdmin);
        return "articleView";
    }

    @GetMapping("/article/new")
    public String createArticleForm(Model model){
        logger.info("Web Request GET /article/new");
        model.addAttribute("isNewArticle", true);
        model.addAttribute("article", new ArticleDto());
        return "articleEdit";
    }

    @PostMapping("/article/new")
    public String createArticle(@ModelAttribute ArticleDto article){
        logger.info("Web Request POST /article/new, new article: {}", article);
        article.setCreationDateTime(LocalDateTime.now());
        UserDto user = userService.getCurrentUserDto();
        article.setAuthor(user);
        ArticleDto newArticle = articleService.addArticle(article, user.getId());
        return "redirect:/article/" + newArticle.getId();
    }

    @PostMapping("/article/{articleId}/comment/new")
    public String appendComment(@ModelAttribute CommentDto newComment, @PathVariable Long articleId, @RequestParam Long authorId) {
        logger.info("Web Request POST /article/{}/comment/new, comment: {}, author: {}, article: {}", articleId, newComment, authorId, articleId);
        return "redirect:/article/"+ articleService.addComment(articleId, authorId, newComment);
    }

    @GetMapping("/article/{articleId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId){
        logger.info("Web Request /article/{}/comment/{}/delete", articleId, commentId);
        return "redirect:/article/"+ articleService.deleteComment(articleId, commentId);
    }
    @GetMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable Long id)
    {
        logger.info("Web Request /article/{}/delete", id);
        if (!userService.isCurrentUserHaveArticle(id) && !userService.isCurrentUserAdmin())
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't delete this article");
        articleService.deleteArticle(id);
        return "redirect:/articles/";
    }

    @GetMapping("/article/{id}/edit")
    public String editArticle(Model model, @PathVariable Long id){
        logger.info("Web Request GET /article/{}/edit", id);
        if (!userService.isCurrentUserHaveArticle(id) && !userService.isCurrentUserAdmin())
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't edit this article");
        model.addAttribute("isNewArticle", false);
        model.addAttribute("article", articleService.getArticle(id));
        return "articleEdit";
    }

    @PostMapping("/article/{id}/update")
    public String updateArticle(Model model, ArticleDto article, @PathVariable Long id){
        logger.info("Web Request POST /article/{}/update, new article: {}", id, article);
        if (!userService.isCurrentUserHaveArticle(id) && !userService.isCurrentUserAdmin())
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't edit this article");
        ArticleDto updatedArticle = articleService.updateArticle(article, id);
        model.addAttribute("article",  updatedArticle);
        return "redirect:/article/"+ updatedArticle.getId();
    }

}
