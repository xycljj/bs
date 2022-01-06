package com.lyh.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Id;

/**
 * type_of_service
 * @author lyh
 */
@Data
public class ServiceType implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 咨询类型名称
     */
    private String name;

    /**
     * 类型服务介绍
     */
    private String introduction;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}