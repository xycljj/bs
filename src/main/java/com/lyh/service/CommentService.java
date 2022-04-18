package com.lyh.service;

import com.lyh.entity.Comment;
import com.lyh.entity.vo.CommentVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName CommentService
 * @createTime 2022/4/18
 */
public interface CommentService {
    List<CommentVo> findComments(Long articleId);

    Comment addComment(Comment comment);
}
