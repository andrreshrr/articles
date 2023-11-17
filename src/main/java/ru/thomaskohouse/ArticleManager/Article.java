package ru.thomaskohouse.ArticleManager;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String head;    //заголовок

    @DateTimeFormat(pattern="dd-MMM-YYYY")
    LocalDateTime creationDateTime;     //время релиза

    @Column(columnDefinition = "TEXT")
    String body;    //основной текст статьи

    @ManyToOne
    User author;    //автор статьи

}
