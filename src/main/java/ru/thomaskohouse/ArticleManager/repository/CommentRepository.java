package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
