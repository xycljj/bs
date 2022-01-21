package com.lyh.entity;

import java.io.Serializable;
import java.util.Date;

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
     * 管理员用户名
     */
    private String adminName;

    /**
     * 密码
     */
    private String adminPassword;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}