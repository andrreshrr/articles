package ru.thomaskohouse.ArticleManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.thomaskohouse.ArticleManager.dict.Sex;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "сущность пользователя")
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Имя")
    String name;

    @Schema(description = "Фамилия")
    String lastName;

    @Schema(description = "Отчество")
    String middleName;

    @Schema(description = "Город проживания")
    String city;

    @Schema(description = "Пол", example = "MALE")
    Sex sex;

    @Schema(description = "Уникальное имя пользователя в системе")
    String username;

    @Schema(description = "Краткая информация о пользователе")
    String about;

    @Schema(description = "Email")
    String email;

    @Schema(description = "Дата рождения")
    LocalDate birthDate;

    @Schema(description = "Роль пользователя")
    String role;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime registrationDateTime;

    public Long getAge(){
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto user = (UserDto) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean isAdmin(){
        return role.equals("ADMIN");
    }
}
