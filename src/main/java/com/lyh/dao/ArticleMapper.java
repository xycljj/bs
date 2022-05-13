package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Article;
import com.lyh.entity.vo.ArticleDetail;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.entity.vo.ArticleVo1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haozidada
 * @date 2022/3/30
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectArticleList(@Param("title") String title, @Param("username") String username, @Param("typeIds") String typeIds);


    List<ArticleVo1> selectListByTypeId(@Param("typeId") Long id);

    ArticleDetail selectArticleDetailById(@Param("id") Long articleId);
}