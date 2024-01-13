package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.Comment;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
public class CommentService {
    final
    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Article addComment(Comment comment, User author, Article article){
        comment.setAuthor(author);
        comment.setCreationDateTime(LocalDateTime.now());
        article.addComment(comment);
        return commentRepository.save(comment).getArticle();
    }
}
