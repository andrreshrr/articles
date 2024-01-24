package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.CommentRepository;
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
    final ArticlesRepository articlesRepository;
    final
    CommentRepository commentRepository;
    final
    UserRepository userRepository;

    public ArticleService(ArticlesRepository articlesRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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

    public Article addComment(Long articleId, Long authorId, Comment comment){
        comment.setAuthor(userRepository.findById(authorId).orElseThrow());
        Article article = articlesRepository.findById(articleId).orElseThrow();
        comment.setArticle(article);
        comment.setCreationDateTime(LocalDateTime.now());
        article.addComment(comment);
        return articlesRepository.save(article);
    }

    public void deleteArticle(Long id){
        articlesRepository.deleteById(id);
    }

    public Article deleteComment(Long articleId, Long commentId){
        Article article = articlesRepository.findById(articleId).orElseThrow();
        article.deleteComment(commentRepository.findById(commentId).orElseThrow());
        return articlesRepository.save(article);
    }
    public Article updateArticle(Article article, Long id){
        Article oldArticle = articlesRepository.findById(id).orElseThrow();
        oldArticle.setHead(article.getHead());
        oldArticle.setBody(article.getBody());

        return articlesRepository.save(oldArticle);
    }

}
