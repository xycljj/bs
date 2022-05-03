package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * question_reply
 * @author 
 */
@Data
public class QuestionAnswer implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 评论用户id
     */
    private Long fromUserId;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String content;

    private Integer isDel;

    private static final long serialVersionUID = 1L;

}