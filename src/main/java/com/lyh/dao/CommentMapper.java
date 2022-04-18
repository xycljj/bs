package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Comment;
import com.lyh.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentVo> selectRootCommentVos(@Param("articleId") Long articleId);

    List<CommentVo> selectChildCommentVos(@Param("articleId") Long articleId, @Param("rootCommentId") Long rootCommentId);
}