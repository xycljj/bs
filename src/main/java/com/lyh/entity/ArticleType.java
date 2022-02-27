package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * article_type
 * @author lyh
 */
@Data
public class ArticleType implements Serializable {

    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 软删除(0未删除,1已删除)
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}