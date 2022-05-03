package com.lyh.entity.vo;

import com.lyh.entity.Report;
import lombok.Data;

/**
 * @author lyh
 * @ClassName ArticleReport
 * @createTime 2022/5/2
 */
@Data
public class ArticleReport extends Report {

    /**
     * 举报人
     */
    private String username;

    /**
     * 被举报人
     */
    private String username1;

    /**
     * 文章标题
     */
    private String title;


}
