package com.lyh.controller;

import com.lyh.entity.Comment;
import com.lyh.entity.vo.CommentVo;
import com.lyh.service.CommentService;
import com.lyh.utils.RedisUtil;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName CommentController
 * @createTime 2022/4/18
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @return
     * @Author lyh
     * @Description 查询所有评论
     * @Param
     * @Date 2022/4/18
     **/
    @GetMapping("findComments")
    public Result<List<CommentVo>> findComments(Long articleId, Long userId) {
        List<CommentVo> list = commentService.findComments(articleId, userId);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 添加评论
     * @Param
     * @Date 2022/4/18
     **/
    @PostMapping("addComment")
    public Result<Comment> addComment(@RequestBody Comment comment) {
        Comment comment1 = commentService.addComment(comment);
        if (comment1.getParentCommentId() == null) {
            redisUtil.sAdd("post:" + comment1.getArticleId() + ":comment:" + comment1.getId(), 0);
        } else {
            redisUtil.incr("post:" + comment1.getArticleId() + ":comment:" + comment1.getId(), 1);
        }
        return ResultUtil.ok(comment1);
    }


    /**
     * @return
     * @Author lyh
     * @Description 点赞评论
     * @Param
     * @Date 2022/4/18
     **/
    @GetMapping("like")
    public Result<Boolean> like(Long userId, Long articleId, Long commentId) {
        redisUtil.sAdd("post:" + articleId + ":comment:" + commentId + ":likeListId", userId);
        Long commentorId = commentService.findCommentorById(commentId);
        redisUtil.incr("getCreditTo"+commentorId,1);
        return ResultUtil.ok(true);
    }

    /**
     * @return
     * @Author lyh
     * @Description 取消点赞评论
     * @Param
     * @Date 2022/4/18
     **/
    @GetMapping("dislike")
    public Result<Boolean> dislike(Long userId, Long articleId, Long commentId) {
        redisUtil.srem("post:" + articleId + ":comment:" + commentId + ":likeListId", userId);
        Long commentorId = commentService.findCommentorById(commentId);
        redisUtil.decr("getCreditTo"+commentorId,1);
        return ResultUtil.ok(true);
    }


}
