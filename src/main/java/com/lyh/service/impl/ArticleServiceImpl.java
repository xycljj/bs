package com.lyh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.dao.ArticleMapper;
import com.lyh.dao.ArticleTypeMapper;
import com.lyh.dao.CommentMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.entity.Comment;
import com.lyh.entity.User;
import com.lyh.entity.vo.ArticleDetail;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.entity.vo.ArticleVo1;
import com.lyh.enums.DelEnum;
import com.lyh.service.ArticleService;
import com.lyh.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int addArticleType(ArticleType articleType) {
        Example example = new Example(ArticleType.class);
        example.createCriteria().andEqualTo("name", articleType.getName());
        if(articleTypeMapper.selectByExample(example).size() > 0){
            return 0;
        }
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
    public List<ArticleVo> findArticleList(String title, String username, String typeIds) {
        List<Article> articleList = articleMapper.selectArticleList(title, username, typeIds);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setArticle(article);
            articleVo.setType(articleTypeMapper.selectByIds(article.getArticleTypeId()));
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
    public List<ArticleVo> getArticlesByUserId(Long userId) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.setOrderByClause("create_time DESC");
        List<Article> articleList = articleMapper.selectByExample(example);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setArticle(article);
            Object readCount = redisUtil.get("post:read:count" + article.getId());
            if (readCount == null) {
                articleVo.setReadCount(0l);
            } else {
                articleVo.setReadCount(Long.parseLong(String.valueOf(readCount)));
            }
            articleVo.setLikeCount(redisUtil.sGetSetSize("post:" + article.getId()));
            Object o = redisUtil.get("collection:post:count" + article.getId());
            if (o == null) {
                articleVo.setCollectionCount(0l);
            } else {
                articleVo.setCollectionCount(Long.parseLong(String.valueOf(o)));
            }
            //文章类型名称
            List<ArticleType> articleTypes = articleTypeMapper.selectByIds(article.getArticleTypeId());
            articleVo.setType(articleTypes);
            articleVo.setUsername(userMapper.selectByPrimaryKey(article.getUserId()).getUsername());
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }

    @Override
    public ArticleDetail getArticlesById(Long articleId) {
        ArticleDetail articleDetail = articleMapper.selectArticleDetailById(articleId);
        articleDetail.setLikeCount(redisUtil.sGetSetSize("post:"+ articleId));
        Object o = redisUtil.get("post:read:count"+articleId);
        if(o == null){
            articleDetail.setReadCount(0);
        }else {
            articleDetail.setReadCount(Integer.parseInt(String.valueOf(o)));
        }
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("articleId",articleId);
        articleDetail.setCommentCount(commentMapper.selectCountByExample(example));
        return articleDetail;
    }

    @Override
    public Long getNewArticleId(String name) {
        Example example = new Example(ArticleType.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        ArticleType articleType = articleTypeMapper.selectOneByExample(example);
        return articleType.getId();
    }

    @Override
    public PageInfo<ArticleVo1> getArticleByTypeId(Long id, Integer pageIndex, Integer pageSize) {
        Page<ArticleVo1> page = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleVo1> list = articleMapper.selectListByTypeId(id);
        for (ArticleVo1 articleVo1 : list) {
            articleVo1.setUsername(userMapper.selectByPrimaryKey(articleVo1.getUserId()).getUsername());
            articleVo1.setType(articleTypeMapper.selectByIds(articleVo1.getArticleTypeId()));
            Object o = redisUtil.get("post:read:count" + articleVo1.getId());
            if(o == null){
                articleVo1.setReadCount(0l);
            }else{
                articleVo1.setReadCount(Long.parseLong(String.valueOf(o)));
            }
        }
        PageInfo<ArticleVo1> articleVo1PageInfo = page.toPageInfo();
        articleVo1PageInfo.setList(list);
        return articleVo1PageInfo;
    }

    @Override
    public Integer countAuthorsArticles(Long id) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("userId", id);
        return articleMapper.selectCountByExample(example);
    }

    @Override
    public boolean editArticle(Article article) {
        article.setCreateTime(new Date());
        return articleMapper.updateByPrimaryKeySelective(article) == 1;
    }

    @Override
    public List<Article> findArticleBySearchStr(String searchStr) {
        Example example = new Example(Article.class);
        example.createCriteria().orLike("title", searchStr).orLike("content", searchStr)
                .andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        return articleMapper.selectByExample(example);
    }

    @Override
    public List<ArticleVo> findArticleByIds(String ids) {
        List<Article> articleList = articleMapper.selectByIds(ids);
        List<ArticleVo> list = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setArticle(article);
            Object readCount = redisUtil.get("post:read:count" + article.getId());
            if (readCount == null) {
                articleVo.setReadCount(0l);
            } else {
                articleVo.setReadCount(Long.parseLong(String.valueOf(readCount)));
            }
            articleVo.setLikeCount(redisUtil.sGetSetSize("post:" + article.getId()));
            // 获得文章的被收藏数
            Object o = redisUtil.get("collection:post:count" + article.getId());
            if (o == null) {
                articleVo.setCollectionCount(0l);
            } else {
                articleVo.setCollectionCount(Long.parseLong(String.valueOf(o)));
            }
            //文章类型名称
            List<ArticleType> articleTypes = articleTypeMapper.selectByIds(article.getArticleTypeId());
            articleVo.setType(articleTypes);
            articleVo.setUsername(userMapper.selectByPrimaryKey(article.getUserId()).getUsername());
            list.add(articleVo);
        }
        return list;
    }

    @Override
    public Long findAuthorByArticleId(Long articleId) {
        return articleMapper.selectByPrimaryKey(articleId).getUserId();
    }

    @Override
    public boolean delArticleById(Long articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setIsDel(DelEnum.IS_DEL.getValue());
        return articleMapper.updateByPrimaryKeySelective(article) == 1;
    }

    @Override
    public List<ArticleType> getArticleTypeListAll() {
        Example example = new Example(ArticleType.class);
        example.createCriteria().andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        List<ArticleType> articleTypes = articleTypeMapper.selectByExample(example);
        return articleTypes;
    }


}
