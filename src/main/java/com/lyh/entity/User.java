package com.lyh.entity;

import java.io.Serializable;
import java.util.Date;

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
    private String phone;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 创建时间/注册时间
     */
    private Date createTime;

    /**
     * 头像
     */
    private String avator;

    /**
     * 性别
     */
    private int gender;

    /**
     * 生日
     */
    private Date birthday;

    private static final long serialVersionUID = 1L;
}