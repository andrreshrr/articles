package ru.thomaskohouse.ArticleManager.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.thomaskohouse.ArticleManager.service.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Конфиг для управления Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    final
    CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {        //определяем форму логина, форму ошибки при логине, ссылку для выхода и запрет на адреса без аутенфикации
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                        .failureUrl("/login-error"))
                        .logout((logout) -> logout.logoutSuccessUrl("/logout"));
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {          //используется при хэшировании пароля
        return new BCryptPasswordEncoder();
    }

}
