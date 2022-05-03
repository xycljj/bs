package com.lyh.entity.vo;

import com.lyh.entity.QaReply;
import com.lyh.entity.QuestionAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName ReplyVo
 * @createTime 2022/4/27
 */
@Data
public class QaVo {

    /**
     * 问答
     */
    private QuestionAnswer questionAnswer;

    /**
     * 回答者用户名
     */
    private String username;

    /**
     * 回答者头像
     */
    private String avatar;

    /**
     * 觉得有用
     */
    private Long useful;

    /**
     * 当前用户是否已经点击有用
     */
    private Boolean isUseful;

    /**
     * 用户是否关注评论者
     */
    private Boolean isFocus;

    /**
     * 回答回复列表
     */
    private List<QaReplyVo> qaReplyList;

    /**
     * 回复数
     */
    private Integer qaReplyNum;

    /**
     * 提问者觉得有用(可佩戴标识)
     */
    private Boolean questionerThinkUseful;

    /**
     * 楼主反馈数
     */
    private Integer feedBack;

    /**
     * 帮助了多少人
     */
    private Integer helpCount;
}
