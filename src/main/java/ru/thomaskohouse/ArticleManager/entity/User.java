package ru.thomaskohouse.ArticleManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import ru.thomaskohouse.ArticleManager.enums.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String lastName;

    String middleName;

    String city;

    Sex sex;

    @Column(nullable = false, unique = true)
    String username;

    @NotNull
          //колонка для хэшированного пароля
    String password;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    boolean enabled;

    @Column(columnDefinition = "TEXT")      //колонка с информацией о пользователе
    String about;
    @Email
    String email;

    LocalDate birthDate;

    LocalDateTime registrationDateTime;

    public Long getAge(){
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
