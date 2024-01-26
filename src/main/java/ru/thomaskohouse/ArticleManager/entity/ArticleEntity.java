package ru.thomaskohouse.ArticleManager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles", schema = "article_service")
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String head;    //заголовок

    @Lazy
    @DateTimeFormat(pattern="dd-MMM-YYYY")
    LocalDateTime creationDateTime;     //время релиза

    @Lazy
    @Column(columnDefinition = "TEXT")
    String body;    //основной текст статьи

    @ManyToOne(cascade = CascadeType.REFRESH)
    UserEntity author;    //автор статьи

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "article", fetch = FetchType.LAZY)   //комменты к статье
    List<CommentEntity> comments = new ArrayList<>();

    public void addComment(CommentEntity comment){    //добавление коммента
        comments.add(comment);
    }
    public void deleteComment(CommentEntity comment){
        comments.remove(comment);
    }

}
