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
    ArticleService articleService;

    Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @GetMapping({"", "/articles", "/articles/"})
    public String firstPage(){
        return "redirect:/articles/1";
    }

    @GetMapping("/articles/{currentPage}")
    public String concretePage(Model model, @PathVariable Integer currentPage){
        logger.info("Web Request to /articles/"+currentPage);
        int pageSize = 10;
        Page<ArticleDto> articlePage = articleService.getPaginated(PageRequest.of(currentPage - 1, pageSize));
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

    @GetMapping("/articles/search")
    public String search(RedirectAttributes redirectAttributes,
                         @RequestParam SearchType searchBy, @RequestParam String searchFor){

        redirectAttributes.addAttribute("searchBy", searchBy);
        redirectAttributes.addAttribute("searchFor", searchFor);

        return "redirect:/articles/search/1";
    }


    @GetMapping("/articles/search/{id}")
    public String searchPage(Model model, @PathVariable Integer id,
                             @RequestParam SearchType searchBy, @RequestParam String searchFor){
        logger.info("Web request to articles/search/{}, searchFor: {}, searchBy: {}", id, searchFor, searchBy);

        //TO-DO

        return "articles";
    }
}
