package com.lyh.entity.vo;

import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName ArticleVo1
 * @createTime 2022/5/7
 */
@Data
public class ArticleVo1 extends Article {

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
