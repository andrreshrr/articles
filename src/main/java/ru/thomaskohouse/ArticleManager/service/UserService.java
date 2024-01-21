package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.entity.Article;
import ru.thomaskohouse.ArticleManager.entity.User;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с юзерами
 */
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ArticlesRepository articlesRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ArticlesRepository articlesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.articlesRepository = articlesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user){
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return  userRepository.findByUsername(username);
    }

    public List<Article> getUserArticles(User user){
        return articlesRepository.findTop5ByAuthorOrderByCreationDateTimeDesc(user);
    }
}
