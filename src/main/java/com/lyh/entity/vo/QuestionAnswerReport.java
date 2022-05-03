package com.lyh.entity.vo;

import com.lyh.entity.Report;
import lombok.Data;

/**
 * @author lyh
 * @ClassName QuestionAnswerReport
 * @createTime 2022/5/3
 */
@Data
public class QuestionAnswerReport extends Report {
    private String username;
    private String username1;
    private String content;
    private Long qId;
}
