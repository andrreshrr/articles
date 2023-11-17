package ru.thomaskohouse.ArticleManager;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


}
