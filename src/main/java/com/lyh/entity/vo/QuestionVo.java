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
     * 觉得有用(总数)
     */
    private Long usefulCount;

    /**
     * 阅读量
     */
    private Long readCount;

    /**
     * 用户头像(回答者)
     */
    private String avatar;

    /**
     * 用户头像(提问者)
     */
    private String questionerAvatar;

    private String questionerUsername;

    /**
     * 是否给了抱抱
     */
    private Boolean isHug;

    /**
     * 抱抱数
     */
    private Long hug;

    /**
     * 是否被收藏
     */
    private Boolean isCollected;

    /**
     * 收藏数
     */
    private Long collected;

    /**
     * 记录当前问答回答有用数最多的回答
     */
    private QaVo qaVo;

}
