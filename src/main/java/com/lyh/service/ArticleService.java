package com.lyh.service;

import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;

import java.util.List;

/**
 * @author lyh
 * @ClassName ArticleService
 * @createTime 2022/2/7
 */
public interface ArticleService {

    Integer addArticleType(ArticleType articleType);

    List<ArticleType> findArticleTypeList();

    Integer delArticleType(Long id);

    boolean addArticle(Article article);
}
