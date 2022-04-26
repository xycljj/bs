package com.lyh.entity.vo;

import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName ArticleVo
 * @createTime 2022/4/1
 */
@Data
public class ArticleVo {

    /**
     * 文章
     */
    private Article article;

    /**
     * 作者用户名
     */
    private String username;

    /**
     * 文章类型名称
     */
    private List<ArticleType> type;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 收藏数
     */
    private Long collectionCount;

    /**
     * 阅读量
     */
    private Long readCount;
}
