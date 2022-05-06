package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * user_test_score
 * @author 
 */
@Data
public class UserTestScore implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 问卷id
     */
    private Long testId;

    /**
     * 得分
     */
    private Integer score;

    private static final long serialVersionUID = 1L;

}