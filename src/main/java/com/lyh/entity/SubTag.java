package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * sub_tag
 * @author 
 */
@Data
public class SubTag implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 问题标签2名
     */
    private String name;

    /**
     * 软删除(0未删除,1已删除)
     */
    private Integer isDel;

    /**
     * 问题标签1 id
     */
    private Long ptagId;

    private static final long serialVersionUID = 1L;


}