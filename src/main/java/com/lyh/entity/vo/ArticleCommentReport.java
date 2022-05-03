package com.lyh.entity.vo;

import com.lyh.entity.Report;
import lombok.Data;

/**
 * @author lyh
 * @ClassName ArticleCommentReport
 * @createTime 2022/5/3
 */
@Data
public class ArticleCommentReport extends Report {
    /**
     * 举报人
     */
    private String username;
    /**
     * 举报对象
     */
    private String username1;
    /**
     * 评论内容
     */
    private String content;
}
