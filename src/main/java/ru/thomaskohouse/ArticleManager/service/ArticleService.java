package ru.thomaskohouse.ArticleManager.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.dict.SearchType;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.CommentDto;
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
import java.util.stream.IntStream;

/**
 * Сервис для работы со статьями
 */
@Service
public class ArticleService {

    private final ArticlesRepository articlesRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public ArticleService(ArticlesRepository articlesRepository, CommentRepository commentRepository, UserRepository userRepository, MappingUtils mappingUtils) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.mappingUtils = mappingUtils;
    }

    public Page<ArticleDto> getPageWithArticles(Pageable pageable){
        return getPageWithArticles(pageable, false, null, null);
    }

    public Page<ArticleDto> getPageWithArticles(Pageable pageable, SearchType searchType, String searchFor){
        return getPageWithArticles(pageable, true, searchType, searchFor);
    }
    
    private Page<ArticleDto> getPageWithArticles(Pageable pageable, Boolean isSearch, @Nullable SearchType searchType, @Nullable String searchFor){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int currentItem = currentPage * pageSize;
        List<ArticleDto> list, allArticles;
        if (!isSearch) {
            allArticles = mappingUtils.mapToListArticleDto(articlesRepository.findAllByOrderByCreationDateTimeDesc());
        } else {
            switch (searchType){
                case BY_TITLE -> allArticles = mappingUtils.mapToListArticleDto(
                        articlesRepository.findAllByHeadContainsIgnoreCaseOrderByCreationDateTimeDesc(searchFor)
                );
                case BY_ARTICLE_BODY -> allArticles = mappingUtils.mapToListArticleDto(
                        articlesRepository.findAllByBodyContainsIgnoreCaseOrderByCreationDateTimeDesc(searchFor)
                );
                case BY_AUTHOR -> allArticles = mappingUtils.mapToListArticleDto(
                        articlesRepository.findAllByAuthor_NameContainsIgnoreCaseOrAuthor_MiddleNameContainsIgnoreCaseOrAuthor_LastNameContainsIgnoreCaseOrderByCreationDateTimeDesc(
                                searchFor, searchFor, searchFor
                        )
                );
                default -> throw new RuntimeException("Wrong search type!");
            }
        }
        if (allArticles.size() < currentItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(currentItem + pageSize, allArticles.size());
            list = allArticles.subList(currentItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), allArticles.size());
    }

    public List<Integer> getPageRange(Integer totalPages){
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
        }
        return pageNumbers;
    }
    
    public ArticleDto getArticle(Long id){
        return mappingUtils.mapToArticleDto(articlesRepository.findById(id).orElseThrow());
    }

    public ArticleDto addArticle(ArticleDto articleDto, Long id){
        ArticleEntity articleEntity = mappingUtils.mapToArticleEntity(articleDto);
        articleEntity.setCreationDateTime(LocalDateTime.now());
        UserEntity author = userRepository.findById(id).orElseThrow();
        articleEntity.setAuthor(author);
        return mappingUtils.mapToArticleDto(articlesRepository.save(articleEntity));
    }

    public Long addComment(Long articleId, Long authorId, CommentDto commentDto){
        CommentEntity commentEntity = mappingUtils.mapToCommentEntity(commentDto);
        commentEntity.setAuthor(userRepository.findById(authorId).orElseThrow());
        ArticleEntity article = articlesRepository.findById(articleId).orElseThrow();
        commentEntity.setArticle(article);
        commentEntity.setCreationDateTime(LocalDateTime.now());
        article.addComment(commentEntity);
        return articlesRepository.save(article).getId();
    }

    public void deleteArticle(Long id){
        articlesRepository.deleteById(id);
    }

    public Long deleteComment(Long articleId, Long commentId){
        ArticleEntity article = articlesRepository.findById(articleId).orElseThrow();
        article.deleteComment(commentRepository.findById(commentId).orElseThrow());
        return articlesRepository.save(article).getId();    //возвращаем Id статьи, у которой был удален коммент
    }
    public ArticleDto updateArticle(ArticleDto articleDto, Long id){
        ArticleEntity oldArticle = articlesRepository.findById(id).orElseThrow();
        oldArticle.setHead(articleDto.getHead());
        oldArticle.setBody(articleDto.getBody());

        return mappingUtils.mapToArticleDto(articlesRepository.save(oldArticle));
    }

}
