package com.lyh.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Id;

/**
 * 管理员操作记录(administrator_operation_information)实体类
 *
 * @author lyh
 * @since 2022-01-26 15:41:24
 * @description 由 Mybatisplus Code Generator 创建
 */

@Data
public class AdministratorOperationInformation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    private Long id;
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 管理员名称(非姓名)
     */
    private String adminName;
    /**
     * 操作信息
     */
    private String message;

}