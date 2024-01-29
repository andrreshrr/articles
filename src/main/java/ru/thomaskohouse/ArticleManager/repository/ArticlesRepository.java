package ru.thomaskohouse.ArticleManager.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;

import java.util.List;

public interface ArticlesRepository extends CrudRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAllByOrderByCreationDateTimeDesc();
    List<ArticleEntity> findTop5ByAuthorIdOrderByCreationDateTimeDesc(Long authorId);
    void deleteById(Long id);

    List<ArticleEntity> findAllByHeadContainsIgnoreCaseOrderByCreationDateTimeDesc(String headSubstring);

    List<ArticleEntity> findAllByBodyContainsIgnoreCaseOrderByCreationDateTimeDesc(String bodySubstring);

    List<ArticleEntity> findAllByAuthor_NameContainsIgnoreCaseOrAuthor_MiddleNameContainsIgnoreCaseOrAuthor_LastNameContainsIgnoreCaseOrderByCreationDateTimeDesc(
            String nameSubstring, String middleNameSubstring, String lastNameSubstring);
}
