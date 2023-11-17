package ru.thomaskohouse.ArticleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller("/")
public class ArticleManagerController {
    @Autowired
    ArticlesRepository articlesRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("articles/")
    public String mainPage(Model model){
        List<Article> articles = (List<Article>) articlesRepository.findAll();
        model.addAttribute("articles", articles);
        return "mainPage";
    }

    @GetMapping("articles/{id}")
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
}
