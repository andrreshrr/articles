package ru.thomaskohouse.ArticleManager.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.service.ArticleService;

@RestController
@RequestMapping("api")
@Tag(name = "Контроллер статей", description = "Позволяет манипулировать статьями")
public class ArticleRestController {

    @Autowired
    final ArticleService articleService;
    Logger logger = LoggerFactory.getLogger(ArticleRestController.class);

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(
            summary = "Получение информации о статье",
            description = "Позволяет получить информацию о конкретной статье по её id"
    )
    @GetMapping("/article/{id}")
    public ArticleDto getArticle(@PathVariable Long id){
        logger.info("API GET /article/{}", id);
        return articleService.getArticle(id);
    }

    @Operation(
            summary = "Создание новой статьи",
            description = "Позволяет создать новую статью, передаваемое тело - тело статьи. Id автора должно быть в параметре запроса"
    )
    @PostMapping("/article/new")
    public ArticleDto createArticle(@RequestBody ArticleDto articleDto, @RequestParam Long authorId){
        logger.info("API POST /article/new, article body: {}, authorId: {}", articleDto, authorId);
        return articleService.addArticle(articleDto, authorId);
    }


    @Operation(
            summary = "Удаление статьи",
            description = "Позволяет удалить статью по её id"
    )
    @DeleteMapping("/article/{id}")
    public String deleteArticle(@PathVariable Long id){
        logger.info("API DELETE /article/{}", id);
        articleService.deleteArticle(id);
        return "success";
    }

}
