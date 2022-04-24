package com.lyh.entity.vo;

import com.lyh.entity.Article;
import lombok.Data;

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
