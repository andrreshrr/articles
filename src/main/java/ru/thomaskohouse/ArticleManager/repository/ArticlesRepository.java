package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.User;

import java.util.List;

public interface ArticlesRepository extends CrudRepository<Article, Long> {
    public List<Article> findAllByOrderByCreationDateTimeDesc();
    public List<Article> findTop5ByAuthorOrderByCreationDateTimeDesc(User author);
}
