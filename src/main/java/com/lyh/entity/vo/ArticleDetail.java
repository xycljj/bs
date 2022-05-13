package com.lyh.entity.vo;

import com.lyh.entity.Article;
import lombok.Data;

/**
 * @author lyh
 * @ClassName ArticleDetail
 * @createTime 2022/5/12
 */
@Data
public class ArticleDetail extends Article {

    private Long likeCount;

    private Integer readCount;

    private Integer commentCount;
}
