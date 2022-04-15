package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * article_like
 * @author lyh
 */
@Data
public class ArticleLike implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 点赞总数
     */
    private Integer likeCount;

    private static final long serialVersionUID = 1L;

}