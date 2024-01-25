package ru.thomaskohouse.ArticleManager.utils;

import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.CommentDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.entity.CommentEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;

@Service
public class MappingUtils {
    public ArticleDto mapToArticleDto(ArticleEntity articleEntity){
        if (articleEntity == null)
            return null;
        ArticleDto articleDto = new ArticleDto();
        articleDto.setBody(articleEntity.getBody());
        articleDto.setHead(articleEntity.getHead());
        articleDto.setId(articleEntity.getId());
        articleDto.setCreationDateTime(articleEntity.getCreationDateTime());
        articleDto.setAuthor(mapToUserDto(articleEntity.getAuthor()));
        //TO-DO comments
        return articleDto;
    }

    public ArticleEntity mapToArticleEntity(ArticleDto articleDto){
        if (articleDto == null)
            return null;
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setBody(articleDto.getBody());
        articleEntity.setHead(articleDto.getHead());
        articleEntity.setId(articleDto.getId());
        articleEntity.setCreationDateTime(articleDto.getCreationDateTime());
        articleEntity.setAuthor(mapToUserEntity(articleDto.getAuthor()));
        //TO-DO comments
        return articleEntity;
    }

    public UserDto mapToUserDto(UserEntity userEntity){
        if (userEntity == null)
            return null;
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setCity(userEntity.getCity());
        userDto.setEmail(userEntity.getEmail());
        userDto.setName(userEntity.getName());
        userDto.setMiddleName(userEntity.getMiddleName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setBirthDate(userEntity.getBirthDate());
        userDto.setAbout(userEntity.getAbout());
        userDto.setRegistrationDateTime(userEntity.getRegistrationDateTime());
        userDto.setSex(userEntity.getSex());
        userDto.setUsername(userEntity.getUsername());
        return  userDto;
    }

    public UserEntity mapToUserEntity(UserDto userDto){
        if (userDto == null){
            return  null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDto.getId());
            userEntity.setCity(userDto.getCity());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setName(userDto.getName());
            userEntity.setMiddleName(userDto.getMiddleName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setBirthDate(userDto.getBirthDate());
            userEntity.setAbout(userDto.getAbout());
            userEntity.setRegistrationDateTime(userDto.getRegistrationDateTime());
            userEntity.setSex(userDto.getSex());
            userEntity.setUsername(userDto.getUsername());
            return  userEntity;
        }
    }

    public CommentDto mapToCommentDto(CommentEntity commentEntity){
        if (commentEntity == null)
            return null;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setBody(commentEntity.getBody());
        commentDto.setCreationDateTime(commentEntity.getCreationDateTime());
        commentDto.setArticle(mapToArticleDto(commentEntity.getArticle()));
        commentDto.setAuthor(mapToUserDto(commentEntity.getAuthor()));
        return commentDto;
    }
    
    public CommentEntity mapToCommentEntity(CommentDto commentDto){
        if (commentDto == null)
            return null;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(commentDto.getId());
        commentEntity.setBody(commentDto.getBody());
        commentEntity.setCreationDateTime(commentDto.getCreationDateTime());
        commentEntity.setArticle(mapToArticleEntity(commentDto.getArticle()));
        commentEntity.setAuthor(mapToUserEntity(commentDto.getAuthor()));
        return commentEntity;
    }
}
