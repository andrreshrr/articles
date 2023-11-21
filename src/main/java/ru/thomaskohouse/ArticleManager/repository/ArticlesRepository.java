package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.Article;

public interface ArticlesRepository extends CrudRepository<Article, Long> {

}
