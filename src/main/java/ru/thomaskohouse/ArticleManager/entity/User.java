package ru.thomaskohouse.ArticleManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

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

    String username;

    @Column(columnDefinition = "UUID")      //колонка для хэшированного пароля MD5
    UUID password;

    @Column(columnDefinition = "TEXT")      //колонка с информацией о пользователе
    String about;

    @Email
    String email;

}
