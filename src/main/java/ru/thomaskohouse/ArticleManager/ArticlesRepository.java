package ru.thomaskohouse.ArticleManager;

import org.springframework.data.repository.CrudRepository;

public interface ArticlesRepository extends CrudRepository<Article, Long> {

}
