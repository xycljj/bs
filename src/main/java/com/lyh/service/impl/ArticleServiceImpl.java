package com.lyh.service.impl;

import com.lyh.dao.ArticleMapper;
import com.lyh.dao.ArticleTypeMapper;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.enums.DelEnum;
import com.lyh.service.ArticleService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName ArticleServiceImpl
 * @createTime 2022/2/7
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleTypeMapper articleTypeMapper;
    @Resource

    private ArticleMapper articleMapper;

    @Override
    public Integer addArticleType(ArticleType articleType) {
        return articleTypeMapper.insert(articleType);
    }

    @Override
    public List<ArticleType> findArticleTypeList() {
        Example example = new Example(ArticleType.class);
        example.createCriteria().andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        return articleTypeMapper.selectByExample(example);
    }

    @Override
    public Integer delArticleType(Long id) {
        ArticleType articleType = new ArticleType();
        articleType.setId(id);
        articleType.setIsDel(DelEnum.IS_DEL.getValue());
        return articleTypeMapper.updateByPrimaryKeySelective(articleType);
    }

    @Override
    public boolean addArticle(Article article) {
        return articleMapper.insert(article) == 1;
    }
}
