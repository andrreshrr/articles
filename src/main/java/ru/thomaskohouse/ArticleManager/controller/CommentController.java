package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.service.ArticleManagerService;
import ru.thomaskohouse.ArticleManager.service.CommentService;

@RestController
public class CommentController {

    @Autowired
    final ArticleManagerService articleService;

    public CommentController(ArticleManagerService commentService) {
        this.articleService = commentService;
    }

    @PostMapping("/comment/new")
    public String addComment(@ModelAttribute Comment newComment,
                             @ModelAttribute Long articleId){
        return "redirect:/article/"+ articleService.addComment(newComment, articleId).getId();
    }

}
