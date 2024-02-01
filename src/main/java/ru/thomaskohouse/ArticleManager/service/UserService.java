package ru.thomaskohouse.ArticleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;
import ru.thomaskohouse.ArticleManager.repository.ArticlesRepository;
import ru.thomaskohouse.ArticleManager.repository.UserRepository;
import ru.thomaskohouse.ArticleManager.utils.MappingUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с юзерами
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    private final ArticlesRepository articlesRepository;

    private final PasswordEncoder passwordEncoder;

    private final MappingUtils mappingUtils;

    @Autowired
    public UserService(UserRepository userRepository, ArticlesRepository articlesRepository, PasswordEncoder passwordEncoder, MappingUtils mappingUtils) {
        this.userRepository = userRepository;
        this.articlesRepository = articlesRepository;
        this.passwordEncoder = passwordEncoder;
        this.mappingUtils = mappingUtils;
    }

    public UserDto addUser(UserDto userDto, String password){
        UserEntity userEntity = mappingUtils.mapToUserEntity(userDto);

        userEntity.setEnabled(true);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRegistrationDateTime(LocalDateTime.now());

        return mappingUtils.mapToUserDto(userRepository.save(userEntity));
    }

    public UserDto updateUser(Long userId, UserDto newUserBody){
        UserEntity user = userRepository.findById(userId).orElseThrow();
        user.setAbout(newUserBody.getAbout());
        user.setCity(newUserBody.getCity());
        user.setEmail(newUserBody.getEmail());
        user.setName(newUserBody.getName());
        user.setLastName(newUserBody.getLastName());
        user.setMiddleName(newUserBody.getMiddleName());
        return mappingUtils.mapToUserDto(userRepository.save(user));
    }

    public UserDto getUser(Long id){
        return mappingUtils.mapToUserDto(userRepository.findById(id).orElseThrow());
    }

    private UserEntity getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return  userRepository.findByUsername(username);
    }
    public UserDto getCurrentUserDto(){
        return isCurrentUserAuthenticated() ? mappingUtils.mapToUserDto(getCurrentUser()) : null;
    }

    public List<ArticleDto> getUserArticles(UserDto user){
        return mappingUtils.mapToListArticleDto(articlesRepository.findTop5ByAuthorIdOrderByCreationDateTimeDesc(user.getId()));
    }

    public boolean isCurrentUserHaveArticle(Long articleId){
        ArticleEntity article = articlesRepository.findById(articleId).orElseThrow();
        return getCurrentUser().equals(article.getAuthor());
    }

    public boolean isCurrentUserAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //тк включена анонимная сессия
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }

    public boolean isCurrentUserAdmin(){
        UserDto currUser = getCurrentUserDto();

        return currUser != null && currUser.isAdmin();
    }
}
