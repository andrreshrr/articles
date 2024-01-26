package ru.thomaskohouse.ArticleManager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "article_service")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "TEXT")
    String body;

    @ManyToOne
    UserEntity author;

    @ManyToOne
    ArticleEntity article;

    LocalDateTime creationDateTime;
}
