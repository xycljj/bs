package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * report
 * @author 
 */
@Data
public class Report implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 举报用户id
     */
    private Long userId;

    /**
     * 被举报的id
     */
    private Long itemId;

    /**
     * 分类(0文章内容,1文章评论,2问题,3回答 4评论)
     */
    private Integer reportType;

    /**
     * 举报时间
     */
    private Date createTime;

    /**
     * 处理时间
     */
    private Date processingTime;

    /**
     * 0未处理,1已处理
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

}