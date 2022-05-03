package com.lyh.entity.vo;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author lyh
 * @ClassName MyQa
 * @createTime 2022/5/1
 */
@Data
public class MyQa {

    /**
     * 提问id
     */
    private Long questionId;

    /**
     * 回答id
     */
    private Long qaId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 问题标题
     */
    private String questionTitle;

    /**
     * 回答内容
     */
    private String qaContent;

    /**
     * 回答点赞数
     */
    private Long usefulCount;

    /**
     * 回答时间
     */
    private Date createTime;

}
