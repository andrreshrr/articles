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
@Table(name = "articles")
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String head;    //заголовок

    @Lazy
    @DateTimeFormat(pattern="dd-MMM-YYYY")
    LocalDateTime creationDateTime;     //время релиза

    @Lazy
    @Column(columnDefinition = "TEXT")
    String body;    //основной текст статьи

    @ManyToOne(cascade = CascadeType.REFRESH)
    User author;    //автор статьи

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "article", fetch = FetchType.LAZY)   //комменты к статье
    List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){    //добавление коммента
        comments.add(comment);
    }
    public void deleteComment(Comment comment){
        comments.remove(comment);
    }

}
