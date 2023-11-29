package ru.thomaskohouse.ArticleManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.UserRepository;
import ru.thomaskohouse.ArticleManager.service.ArticleManagerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class ArticlesController {

    @Autowired
    ArticleManagerService service;

    @GetMapping("")
    public String mainFirstPage(){
        return "redirect:/articles/1";
    }
    @GetMapping("/articles")
    public String mainPage(){
        return "redirect:/articles/1";
    }

    @GetMapping("/articles/")
    public String mainOnePage(){
        return "redirect:/articles/1";
    }

    @GetMapping("/articles/{currentPage}")
    public String articlesPage(Model model, @PathVariable Integer currentPage){
        int pageSize = 10;
        Page<Article> articlePage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("articlePage", articlePage);
        int totalPages = articlePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        return "articleList";
    }


}
