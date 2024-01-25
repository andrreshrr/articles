package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.CommentEntity;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
}
