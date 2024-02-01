package ru.thomaskohouse.ArticleManager.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.thomaskohouse.ArticleManager.dict.SearchType;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.service.ArticleService;


/**
 * Манипуляция списком статей
 * Вывод, загрузка страниц
 */
@Controller
public class ArticlesController {
    private final ArticleService articleService;

    private final int pageSize = 10;

    private final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"", "/articles", "/articles/"})
    public String firstPage(){
        return "redirect:/articles/1";
    }

    @GetMapping("/articles/{currentPage}")
    public String concretePage(Model model, @PathVariable Integer currentPage){
        logger.info("Web Request to /articles/"+currentPage);

        Page<ArticleDto> articlePage = articleService.getPageWithArticles(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("pageNumbers",  articleService.getPageRange(articlePage.getTotalPages()));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("isSearch", false);

        return "articles";
    }

    @GetMapping("/articles/search")
    public String search(RedirectAttributes redirectAttributes,
                         @RequestParam SearchType searchBy, @RequestParam String searchFor){

        redirectAttributes.addAttribute("searchBy", searchBy);
        redirectAttributes.addAttribute("searchFor", searchFor);

        return "redirect:/articles/search/1";
    }


    @GetMapping("/articles/search/{currentPage}")
    public String searchPage(Model model, @PathVariable Integer currentPage,
                             @RequestParam SearchType searchBy, @RequestParam String searchFor){
        logger.info("Web request to articles/search/{}, searchFor: {}, searchBy: {}", currentPage, searchFor, searchBy);

        Page<ArticleDto> articlePage = articleService.getPageWithArticles(PageRequest.of(currentPage - 1, pageSize), searchBy, searchFor);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("pageNumbers",  articleService.getPageRange(articlePage.getTotalPages()));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("isSearch", true);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("searchFor", searchFor);

        return "articles";
    }
}
