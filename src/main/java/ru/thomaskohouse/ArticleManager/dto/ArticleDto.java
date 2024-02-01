package ru.thomaskohouse.ArticleManager.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Сущность статьи")
public class ArticleDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Заголовок статьи")
    String head;

    @Schema(description = "Текст статьи")
    String body;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime creationDateTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    UserDto author;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonManagedReference
    List<CommentDto> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", author=" + author +
                ", comments=" + comments +
                '}';
    }
}
