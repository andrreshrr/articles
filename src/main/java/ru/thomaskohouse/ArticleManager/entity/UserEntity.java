package ru.thomaskohouse.ArticleManager.entity;

import jakarta.persistence.*;
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
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity user = (UserEntity) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lazy
    String name;

    String lastName;

    @Lazy
    String middleName;

    @Lazy
    String city;

    @Lazy
    Sex sex;

    @Column(nullable = false, unique = true)
    String username;

    @NotNull
          //колонка для хэшированного пароля
    String password;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    boolean enabled;

    @Lazy
    @Column(columnDefinition = "TEXT")      //колонка с информацией о пользователе
    String about;

    @Lazy
    @Email
    String email;

    @Lazy
    LocalDate birthDate;

    @Lazy
    LocalDateTime registrationDateTime;

    public Long getAge(){
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
