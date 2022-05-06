package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * test_score_rule
 * @author 
 */
@Data
public class TestScoreRule implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 分数段,用,分隔
     */
    private String segment;

    /**
     * 结果
     */
    private String result;

    private static final long serialVersionUID = 1L;

}