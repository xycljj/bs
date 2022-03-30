package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * article
 * @author 
 */
@Data
public class Article implements Serializable {

    @Id
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 软删除(0未删除,1已删除)
     */
    private String isDel;

    /**
     * 文章类型id
     */
    private Long articleTypeId;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

}