package ru.thomaskohouse.ArticleManager.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.service.ArticleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Манипуляция списком статей
 * Вывод, загрузка страниц
 */
@Controller
public class ArticlesController {

    @Autowired
    ArticleService service;

    Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @GetMapping({"", "/articles", "/articles/"})
    public String firstPage(){
        return "redirect:/articles/1";
    }

    @GetMapping("/articles/{currentPage}")
    public String concretePage(Model model, @PathVariable Integer currentPage){
        logger.info("Web Request to /articles/"+currentPage);
        int pageSize = 10;
        Page<ArticleDto> articlePage = service.getPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("articlePage", articlePage);
        int totalPages = articlePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        return "articles";
    }
}
