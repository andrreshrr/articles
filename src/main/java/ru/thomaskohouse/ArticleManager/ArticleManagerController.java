package ru.thomaskohouse.ArticleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller("/")
public class ArticleManagerController {
    @Autowired
    ArticlesRepository articlesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleManagerService service;

    @GetMapping("articles/")
    public String mainPage(){
        return "redirect:/articles/1";
    }

    @GetMapping("articles")
    public String mainOnePage(){
        return "redirect:/articles/1";
    }
    @GetMapping("articles/{pageNum}")
    public String articlesPage(Model model, @PathVariable Optional<Integer> pageNum){
        int currentPage = pageNum.orElse(1);
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
        return "mainPage";
    }

    @GetMapping("/article/{id}")
    public String articlePage(@PathVariable Long id, Model model){
        Article article = articlesRepository.findById(id).get();
        User author = article.author;

        model.addAttribute("article", article);
        model.addAttribute("author", author);
        return "articlePage";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/article/new")
    public String writeArticle(Model model){
        model.addAttribute("article", new Article());
        return "newArticle";
    }

    @PostMapping("/article/create")
    public ResponseEntity<Article> createArticle(Model model, @ModelAttribute Article article){
        article.setCreationDateTime(LocalDateTime.now());
        Article newArticle = articlesRepository.save(article);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }
}
