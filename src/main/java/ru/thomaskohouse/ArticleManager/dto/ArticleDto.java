package ru.thomaskohouse.ArticleManager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {
    Long id;
    String head;
    String body;
    LocalDateTime creationDateTime;
    UserDto author;
    List<CommentDto> comments;

}
