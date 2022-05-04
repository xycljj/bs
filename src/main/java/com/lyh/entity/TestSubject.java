package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * test_subject
 * @author 
 */
@Data
public class TestSubject implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 问题类型(多选,单选,填空...)
     */
    private String type;

    /**
     * 问题题目
     */
    private String title;

    /**
     * 选项/答案(可以用,分隔)
     */
    private String answer;

    /**
     * 每道题的分数
     */
    private String score;

    private static final long serialVersionUID = 1L;

}