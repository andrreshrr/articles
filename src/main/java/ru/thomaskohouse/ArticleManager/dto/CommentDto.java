package ru.thomaskohouse.ArticleManager.dto;

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
    UserDto author;
    ArticleDto article;
    LocalDateTime creationDateTime;
}
