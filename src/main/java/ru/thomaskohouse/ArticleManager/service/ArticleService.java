package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.UserRepository;
import ru.thomaskohouse.ArticleManager.entity.Article;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Сервис для работы со статьями
 */
@Service
public class ArticleService {
    @Autowired
    final ArticlesRepository articlesRepository;

    public ArticleService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public Page<Article> getPaginated(Pageable pageable){      //получить страницу со статьями
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int currentItem = currentPage * pageSize;
        List<Article> list;
        List<Article> allArticles = articlesRepository.findAllByOrderByCreationDateTimeDesc();
        if (allArticles.size() < currentItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(currentItem + pageSize, allArticles.size());
            list = allArticles.subList(currentItem, toIndex);
        }
        Page<Article> articlePage = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), allArticles.size());
        return articlePage;
    }

    public Article getArticle(Long id) {
        return articlesRepository.findById(id).orElseThrow();
    }

    public Article addArticle(Article newArticle){
        return  articlesRepository.save(newArticle);
    }

    public Article addComment(Comment comment){
        comment.setCreationDateTime(LocalDateTime.now());
        Article article = comment.getArticle();
        article.addComment(comment);
        return articlesRepository.save(article);
    }

    public void deleteArticle(Long id){
        articlesRepository.deleteById(id);
    }

    public Article updateArticle(Article article, Long id){
        Article oldArticle = articlesRepository.findById(id).orElseThrow();
        oldArticle.setHead(article.getHead());
        oldArticle.setBody(article.getBody());

        return articlesRepository.save(oldArticle);
    }

}
