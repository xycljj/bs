package com.lyh.entity.vo;

import com.lyh.entity.Report;
import lombok.Data;

/**
 * @author lyh
 * @ClassName QuestionAnswerReplyReport
 * @createTime 2022/5/3
 */
@Data
public class QuestionAnswerReplyReport extends Report {
    private String username;
    private String username1;
    private String content;
}
