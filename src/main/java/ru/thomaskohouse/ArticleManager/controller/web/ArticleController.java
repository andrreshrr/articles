package ru.thomaskohouse.ArticleManager.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.ArticleService;
import ru.thomaskohouse.ArticleManager.service.UserService;

import java.time.LocalDateTime;


/**
 * Контроллер для манипуляции одной статьей.
 * CRUD, добавление коммента
 */
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable Long id, Model model){
        Article article = articleService.getArticle(id); //загружаем тело статьи
        User author = article.getAuthor();                      //загружаем автора
        Comment newComment = new Comment();                     //готовим метаданные комментария
        User currentUser = userService.getCurrentUser();
        boolean isCurrentUser = author.equals(currentUser);

        model.addAttribute("article", article);
        model.addAttribute("author", author);
        model.addAttribute("newComment", newComment);
        model.addAttribute("currentUser", currentUser);

        return "articleView";
    }

    @GetMapping("/article/new")
    public String createArticleForm(Model model){
        model.addAttribute("isNewArticle", true);
        model.addAttribute("article", new Article());
        return "articleEdit";
    }

    @PostMapping("/article/new")
    public String createArticle(@ModelAttribute Article article){
        article.setCreationDateTime(LocalDateTime.now());
        User user = userService.getCurrentUser();
        article.setAuthor(user);
        Article newArticle = articleService.addArticle(article);
        return "redirect:/article/"+newArticle.getId();
    }

    @PostMapping("/article/{articleId}/comment/new")
    public String appendComment(@ModelAttribute Comment newComment, @PathVariable Long articleId, @RequestParam Long authorId) {
        return "redirect:/article/"+ articleService.addComment(articleId, authorId, newComment).getId();
    }

    @GetMapping("/article/{articleId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId){
        return "redirect:/article/"+ articleService.deleteComment(articleId, commentId).getId();
    }
    @GetMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable Long id)
    {
        if (!userService.isCurrentUserHaveArticle(id))
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't delete this article");
        articleService.deleteArticle(id);
        return "redirect:/articles/";
    }

    @GetMapping("/article/{id}/edit")
    public String editArticle(Model model, @PathVariable Long id){
        if (!userService.isCurrentUserHaveArticle(id))
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't edit this article");
        model.addAttribute("isNewArticle", false);
        model.addAttribute("article", articleService.getArticle(id));
        return "articleEdit";
    }

    @PostMapping("/article/{id}/update")
    public String updateArticle(Model model, Article article, @PathVariable Long id){
        if (!userService.isCurrentUserHaveArticle(id))
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "You can't edit this article");
        Article updatedArticle = articleService.updateArticle(article, id);
        model.addAttribute("article",  updatedArticle);
        return "redirect:/article/"+ updatedArticle.getId();
    }

}