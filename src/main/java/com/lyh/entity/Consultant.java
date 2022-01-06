package com.lyh.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Id;

/**
 * psychological_consultant
 * @author 
 */
@Data
public class Consultant implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 咨询师名称
     */
    private String consultantName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 服务类型
     */
    private Long serviceTypeId;

    /**
     * 服务类型名称
     */
    private String serviceTypeName;

    /**
     * 软删除（0为未删除，1为已删除）
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}