package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * user_like_article
 * @author lyh
 */
@Data
public class UserLikeArticle implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 点赞用户id
     */
    private Long userId;

    /**
     * 被点赞的文章id
     */
    private Long articleId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}