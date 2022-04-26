package com.lyh.entity.vo;

import com.lyh.entity.Question;
import com.lyh.entity.SubTag;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionVo
 * @createTime 2022/4/26
 */
@Data
public class QuestionVo {

    /**
     * 问题
     */
    private Question question;

    /**
     * 标签2
     */
    private List<SubTag> subTagList;

    /**
     * 回答数
     */
    private Integer answerCount;

    /**
     * 觉得有用
     */
    private Integer usefulCount;

    /**
     * 用户头像(回答者)
     */
    private String avatar;
}
