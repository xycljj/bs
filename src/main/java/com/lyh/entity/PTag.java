package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * p_tag
 * @author lyh
 */
@Data
public class PTag implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 问题标签1名
     */
    private String name;

    /**
     * 软删除(0未删除,1已删除)
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;

}