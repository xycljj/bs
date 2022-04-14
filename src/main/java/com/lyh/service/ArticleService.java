package com.lyh.service;

import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.entity.vo.ArticleVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName ArticleService
 * @createTime 2022/2/7
 */
public interface ArticleService {

    int addArticleType(ArticleType articleType);

    List<ArticleType> findArticleTypeList();

    Integer delArticleType(Long id);

    boolean addArticle(Article article);

    List<ArticleVo> findArticleList(String title, String username);

    void changeArticleCover(Long articleId, String url);

    List<Article> getArticlesByUserId(Long userId);

    Article getArticlesById(Long articleId);

    Long getNewArticleId(String name);

    List<Article> getArticleByTypeId(Long id);

    Integer countAuthorsArticles(Long id);
}
