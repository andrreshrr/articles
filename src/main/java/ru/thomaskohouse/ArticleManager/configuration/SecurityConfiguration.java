package ru.thomaskohouse.ArticleManager.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.thomaskohouse.ArticleManager.service.CustomUserDetailsService;

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
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/api*").permitAll()); //отключить csrf-токен при работе с API
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                        .failureUrl("/login-error"))
                        .logout((logout) -> logout.logoutSuccessUrl("/logout-miss"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/article/new").authenticated()        //для создания новой статьи надо залогиниться
                        .requestMatchers(HttpMethod.POST, "/article/new").authenticated()
                        .requestMatchers(HttpMethod.POST, "/comment/new").authenticated()
                        .requestMatchers(HttpMethod.GET, "/article/*/delete").authenticated()
                        .requestMatchers(HttpMethod.GET, "/article/*/edit").authenticated()
                        .requestMatchers(HttpMethod.POST, "/article/*/update").authenticated()
                        .requestMatchers(HttpMethod.GET, "/user/profile").authenticated()
                        .anyRequest().permitAll());                                               //остальные запросы можно делать без аутенфикации
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {          //используется при хэшировании пароля
        return new BCryptPasswordEncoder();
    }

}
