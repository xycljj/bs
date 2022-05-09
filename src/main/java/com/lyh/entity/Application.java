package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * application
 * @author 
 */
@Data
public class Application implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 上传文件的ids
     */
    private String urlIds;

    /**
     * 申请时间
     */
    private Date createTime;

    /**
     * 是否通过(1通过 0 未通过)
     */
    private Integer isPass;

    private String skillField;

    private static final long serialVersionUID = 1L;

}