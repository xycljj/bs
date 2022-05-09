package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleTypeMapper extends BaseMapper<ArticleType> {

    List<Article> selectAllWhileIsNotDel();


}