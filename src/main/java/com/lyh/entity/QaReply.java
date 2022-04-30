package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * qa_reply
 * @author 
 */
@Data
public class QaReply implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 评论者id
     */
    private Long userId;

    /**
     * 回复时间
     */
    private Date createTime;

    /**
     * 回答id
     */
    private Long qaId;

    private Integer isDel;

    private static final long serialVersionUID = 1L;

}