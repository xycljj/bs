package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.entity.vo.ArticleDetail;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.entity.vo.ArticleVo1;

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

    List<ArticleVo> findArticleList(String title, String username, String typeIds);

    void changeArticleCover(Long articleId, String url);

    List<ArticleVo> getArticlesByUserId(Long userId);

    ArticleDetail getArticlesById(Long articleId);

    Long getNewArticleId(String name);

    PageInfo<ArticleVo1> getArticleByTypeId(Long id, Integer pageIndex, Integer pageSize);

    Integer countAuthorsArticles(Long id);

    boolean editArticle(Article article);

    List<Article> findArticleBySearchStr(String searchStr);

    List<ArticleVo> findArticleByIds(String ids);

    Long findAuthorByArticleId(Long articleId);

    boolean delArticleById(Long articleId);

    List<ArticleType> getArticleTypeListAll();


}
