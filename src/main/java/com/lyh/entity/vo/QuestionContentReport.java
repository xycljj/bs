package com.lyh.entity.vo;

import com.lyh.entity.Report;
import lombok.Data;

/**
 * @author lyh
 * @ClassName QuestionContentReport
 * @createTime 2022/5/3
 */
@Data
public class QuestionContentReport extends Report {
    /**
     * 举报人
     */
    private String username;
    /**
     * 举报对象
     */
    private String username1;
    /**
     * 问题标题
     */
    private String title;
}
