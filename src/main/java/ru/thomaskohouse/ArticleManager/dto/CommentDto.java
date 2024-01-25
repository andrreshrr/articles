package ru.thomaskohouse.ArticleManager.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    Long id;
    String body;
    @JsonBackReference
    UserDto author;
    @JsonBackReference
    ArticleDto article;
    LocalDateTime creationDateTime;
}
