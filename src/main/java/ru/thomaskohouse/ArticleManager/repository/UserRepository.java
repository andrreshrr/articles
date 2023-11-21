package ru.thomaskohouse.ArticleManager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thomaskohouse.ArticleManager.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
