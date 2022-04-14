package com.lyh.service.impl;

import com.lyh.dao.ArticleMapper;
import com.lyh.dao.ArticleTypeMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.ArticleService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private UserMapper userMapper;

    @Override
    public int addArticleType(ArticleType articleType) {
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
        article.setCreateTime(new Date());
        article.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return articleMapper.insert(article) == 1;
    }

    @Override
    public List<ArticleVo> findArticleList(String title, String username) {
        List<Article> articleList = articleMapper.selectArticleList(title,username);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for(Article article : articleList){
            ArticleVo articleVo = new ArticleVo();
            articleVo.setArticle(article);
            articleVo.setUsername(userMapper.selectByPrimaryKey(article.getUserId()).getUsername());
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }

    @Override
    public void changeArticleCover(Long articleId, String url) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        article.setCover(url);
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public List<Article> getArticlesByUserId(Long userId) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("userId",userId);
        return articleMapper.selectByExample(example);
    }

    @Override
    public Article getArticlesById(Long articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public Long getNewArticleId(String name) {
        Example example = new Example(ArticleType.class);
        example.createCriteria().andEqualTo("name",name).andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        ArticleType articleType = articleTypeMapper.selectOneByExample(example);
        return articleType.getId();
    }

    @Override
    public List<Article> getArticleByTypeId(Long id) {
        List<Article> articleList = articleMapper.selectAll();
        List<Article> useArticleList = new ArrayList<>();
        for(Article article: articleList){
            String[] split = article.getArticleTypeId().split(",");
            for(String _id: split){
                if(String.valueOf(id).equals(_id)) {
                    useArticleList.add(article);
                    break;
                }
            }
        }
        return useArticleList;
    }

    @Override
    public Integer countAuthorsArticles(Long id) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("userId",id);
        return articleMapper.selectCountByExample(example);
    }
}
