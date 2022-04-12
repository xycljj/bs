package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haozidada
 * @date 2022/3/30
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectArticleList(@Param("title") String title, @Param("userId") Long userId);
}