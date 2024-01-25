package ru.thomaskohouse.ArticleManager.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.service.ArticleService;

@RestController
@RequestMapping("api")
public class ArticleRestController {

    @Autowired
    final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/{id}")
    public ArticleDto getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }

    @PostMapping("/article/new")
    public ArticleDto createArticle(@RequestBody ArticleDto articleDto, @RequestParam Long authorId){
        return articleService.addArticle(articleDto, authorId);
    }

    @DeleteMapping("/article/{id}")
    public String deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return "success";
    }

}
