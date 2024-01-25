package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;

import java.util.List;

public interface ArticlesRepository extends CrudRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAllByOrderByCreationDateTimeDesc();
    List<ArticleEntity> findTop5ByAuthorIdOrderByCreationDateTimeDesc(Long authorId);
    void deleteById(Long id);
}
