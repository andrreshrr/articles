package ru.thomaskohouse.ArticleManager.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import ru.thomaskohouse.ArticleManager.dict.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    Long id;

    String name;

    String lastName;

    String middleName;

    String city;

    Sex sex;

    String username;

    String about;

    String email;

    LocalDate birthDate;

    LocalDateTime registrationDateTime;

    public Long getAge(){
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
