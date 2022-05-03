package com.lyh.entity.vo;

import com.lyh.entity.QuestionAnswer;
import lombok.Data;

/**
 * @author lyh
 * @ClassName QuestionAnswerVo
 * @createTime 2022/5/3
 */
@Data
public class QuestionAnswerVo extends QuestionAnswer {

    /**
     * 问题标题
     */
    private String title;

    /**
     * 回答用户
     */
    private String username;

    /**
     * 提问用户
     */
    private String username1;


}
