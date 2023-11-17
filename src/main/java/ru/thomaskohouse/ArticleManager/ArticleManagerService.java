package ru.thomaskohouse.ArticleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleManagerService {
    @Autowired
    ArticlesRepository articlesRepository;
    public Page<Article> findPaginated(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int currentItem = currentPage * pageSize;
        List<Article> list;
        List<Article> allArticles = (List<Article>) articlesRepository.findAll();
        if (allArticles.size() < currentItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(currentItem + pageSize, allArticles.size());
            list = allArticles.subList(currentItem, toIndex);
        }
        Page<Article> articlePage = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), allArticles.size());
        return articlePage;
    }
}
