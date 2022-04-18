package com.lyh.entity.vo;

import com.lyh.entity.Comment;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author lyh
 * @ClassName CommentVo
 * @createTime 2022/4/18
 */
@Data
public class CommentVo extends Comment {

    /**
     * 评论者
     */
    private String username;

    /**
     * 被评论者
     */
    private String _username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 评论点赞数
     */
    private Long like;

    /**
     * 用户是否点赞该评论
     */
    private Boolean isLike;

    /**
     * 点赞用户ids
     */
    private Set<Object> likeListId;

    private Long commentNum;

    /**
     * 子评论
     */

    /**
     * 不自动显示
     */
    private Boolean inputShow = false;
    private List<CommentVo> reply;
}
