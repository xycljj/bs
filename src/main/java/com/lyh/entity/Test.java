package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * test
 * @author 
 */
@Data
public class Test implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 问卷名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date date;

    /**
     * 题目ids
     */
    private String itemIds;

    /**
     * 软删除(0未删除,1已删除)
     */
    private Integer isDel;

    /**
     * 测试封面
     */
    private String cover;

    private static final long serialVersionUID = 1L;


}