package ru.thomaskohouse.ArticleManager.dict;

import lombok.Getter;

@Getter
public enum SearchType {
    BY_TITLE("By title"),
    BY_AUTHOR("By author"),
    BY_ARTICLE_BODY("By article body");
    private final String displayValue;

    SearchType(String displayValue) {
        this.displayValue = displayValue;
    }

}
