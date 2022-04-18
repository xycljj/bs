package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * comment
 * @author 
 */
@Data
public class Comment implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 父评论id
     */
    private Long parentCommentId;

    /**
     * 根评论id
     */
    private Long rootCommentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String content;

    private static final long serialVersionUID = 1L;


}