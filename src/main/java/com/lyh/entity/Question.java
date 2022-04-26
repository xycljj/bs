package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * question
 * @author 
 */
@Data
public class Question implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 标签1id
     */
    private Long ptagId;

    /**
     * 标签1名称
     */
    private String ptagName;

    /**
     * 标签2ids(最多可选三个)
     */
    private String subTagIds;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否匿名(0实名,1匿名)
     */
    private Integer isRealName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 软删除(0未删除,1已删除)
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;

}