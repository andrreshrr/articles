package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.CommentEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.CommentRepository;
import ru.thomaskohouse.ArticleManager.repository.UserRepository;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.utils.MappingUtils;

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
    @Autowired
    final CommentRepository commentRepository;
    @Autowired
    final UserRepository userRepository;
    @Autowired
    final MappingUtils mappingUtils;

    public ArticleService(ArticlesRepository articlesRepository, CommentRepository commentRepository, UserRepository userRepository, MappingUtils mappingUtils) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.mappingUtils = mappingUtils;
    }

    public Page<ArticleEntity> getPaginated(Pageable pageable){      //получить страницу со статьями
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int currentItem = currentPage * pageSize;
        List<ArticleEntity> list;
        List<ArticleEntity> allArticles = articlesRepository.findAllByOrderByCreationDateTimeDesc();
        if (allArticles.size() < currentItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(currentItem + pageSize, allArticles.size());
            list = allArticles.subList(currentItem, toIndex);
        }
        Page<ArticleEntity> articlePage = new PageImpl<ArticleEntity>(list, PageRequest.of(currentPage, pageSize), allArticles.size());
        return articlePage;
    }

    public ArticleEntity getArticle(Long id) {
        return articlesRepository.findById(id).orElseThrow();
    }

    public ArticleDto getArticleDto(Long id){
        return mappingUtils.mapToArticleDto(articlesRepository.findById(id).orElseThrow());
    }

    public ArticleDto addArticle(ArticleDto articleDto, Long id){
        ArticleEntity articleEntity = mappingUtils.mapToArticleEntity(articleDto);
        articleEntity.setCreationDateTime(LocalDateTime.now());
        UserEntity author = userRepository.findById(id).orElseThrow();
        articleEntity.setAuthor(author);
        return mappingUtils.mapToArticleDto(articlesRepository.save(articleEntity));
    }

    public ArticleEntity addArticle(ArticleEntity newArticle){
        return  articlesRepository.save(newArticle);
    }

    public ArticleEntity addComment(Long articleId, Long authorId, CommentEntity comment){
        comment.setAuthor(userRepository.findById(authorId).orElseThrow());
        ArticleEntity article = articlesRepository.findById(articleId).orElseThrow();
        comment.setArticle(article);
        comment.setCreationDateTime(LocalDateTime.now());
        article.addComment(comment);
        return articlesRepository.save(article);
    }

    public void deleteArticle(Long id){
        articlesRepository.deleteById(id);
    }

    public ArticleEntity deleteComment(Long articleId, Long commentId){
        ArticleEntity article = articlesRepository.findById(articleId).orElseThrow();
        article.deleteComment(commentRepository.findById(commentId).orElseThrow());
        return articlesRepository.save(article);
    }
    public ArticleEntity updateArticle(ArticleEntity article, Long id){
        ArticleEntity oldArticle = articlesRepository.findById(id).orElseThrow();
        oldArticle.setHead(article.getHead());
        oldArticle.setBody(article.getBody());

        return articlesRepository.save(oldArticle);
    }

}
