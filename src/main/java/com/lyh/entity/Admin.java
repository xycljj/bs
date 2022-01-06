package com.lyh.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Id;

/**
 * admin
 * @author 
 */
@Data
public class Admin implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    private String adminName;

    /**
     * 密码
     */
    private String adminPassword;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}