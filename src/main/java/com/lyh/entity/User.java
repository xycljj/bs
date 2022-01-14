package com.lyh.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Id;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户类型（0为普通用户，1为心理咨询师，2为管理员）
     */
    private Integer userType;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    /**
     * 身份证号码s
     */
    private String idCard;

    private static final long serialVersionUID = 1L;
}