package ru.thomaskohouse.ArticleManager.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thomaskohouse.ArticleManager.dto.ArticleDto;
import ru.thomaskohouse.ArticleManager.dto.CommentDto;
import ru.thomaskohouse.ArticleManager.dto.UserDto;
import ru.thomaskohouse.ArticleManager.entity.ArticleEntity;
import ru.thomaskohouse.ArticleManager.entity.CommentEntity;
import ru.thomaskohouse.ArticleManager.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingUtils {

    @Autowired
    final ModelMapper modelMapper;

    public MappingUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArticleDto mapToArticleDto(ArticleEntity articleEntity){
        return modelMapper.map(articleEntity, ArticleDto.class);
    }

    public ArticleEntity mapToArticleEntity(ArticleDto articleDto){
       return modelMapper.map(articleDto, ArticleEntity.class);
    }

    public UserDto mapToUserDto(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity mapToUserEntity(UserDto userDto){
        return  modelMapper.map(userDto, UserEntity.class);
    }


    public CommentDto mapToCommentDto(CommentEntity commentEntity){
        return modelMapper.map(commentEntity, CommentDto.class);
    }
    
    public CommentEntity mapToCommentEntity(CommentDto commentDto){
        return  modelMapper.map(commentDto, CommentEntity.class);
    }

    public List<ArticleDto> mapToListArticleDto(List<ArticleEntity> articleEntities){
        if (articleEntities == null)
            return null;
        List<ArticleDto> articleDtoList = new ArrayList<>(articleEntities.size());
        for (ArticleEntity articleEntity : articleEntities) {
            articleDtoList.add(mapToArticleDto(articleEntity));
        }
        return articleDtoList;
    }

}
