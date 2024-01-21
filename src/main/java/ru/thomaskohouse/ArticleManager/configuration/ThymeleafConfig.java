package ru.thomaskohouse.ArticleManager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

/**
 * Конфигурация для Thymeleaf, чтобы в нём можно было использовать выражения для проверки текущего пользователя из Spring Security
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
