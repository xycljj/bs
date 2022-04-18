package com.lyh.service.impl;

import com.lyh.dao.CommentMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Comment;
import com.lyh.entity.User;
import com.lyh.entity.vo.CommentVo;
import com.lyh.service.CommentService;
import com.lyh.utils.RedisUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName CommentServiceImpl
 * @createTime 2022/4/18
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public List<CommentVo> findComments(Long articleId) {
        /**
         * 思路: 找到根评论,所有根评论下的回复都作为根评论的子评论来处理,每个子评论中的父评论id作为回复对象,然后进行
         * 按照时间倒序排序即可
         */

        // 根评论list
        List<CommentVo> commentVos = commentMapper.selectRootCommentVos(articleId);

        for (CommentVo commentVo : commentVos) {
            User user = userMapper.selectByPrimaryKey(commentVo.getUserId());
            commentVo.setAvatar(user.getAvatar());
            commentVo.setUsername(user.getUsername());
            commentVo.setLike(redisUtil.sGetSetSize("post:" + commentVo.getArticleId() + ":comment:" + commentVo.getId() + ":likeListId"));
            commentVo.setIsLike(redisUtil.sHasKey("post:" + commentVo.getArticleId() + ":comment:" + commentVo.getId() + ":likeListId",commentVo.getUserId()));
//            commentVo.setCommentNum((Long) redisUtil.get("post:" + commentVo.getArticleId() + ":comment:" + commentVo.getId()));
            commentVo.setLikeListId(redisUtil.sGet("post:" + commentVo.getArticleId() + ":comment:" + commentVo.getId() + ":likeListId"));
            List<CommentVo> commentVos1 = commentMapper.selectChildCommentVos(articleId, commentVo.getId());
            for (CommentVo commentVo1 : commentVos1) {
                //当前用户 /评论者
                User user1 = userMapper.selectByPrimaryKey(commentVo1.getUserId());
                commentVo1.setAvatar(user1.getAvatar());
                commentVo1.setUsername(user1.getUsername());
                // 获取父评论的id
                Comment comment = commentMapper.selectByPrimaryKey(commentVo1.getParentCommentId());
                // 获取父评论的用户id 以获取父评论用户的用户名
                User user2 = userMapper.selectByPrimaryKey(comment.getUserId());
                commentVo1.set_username(user2.getUsername());

                commentVo1.setLike(redisUtil.sGetSetSize("post:" + commentVo1.getArticleId() + ":comment:" + commentVo1.getId() + ":likeListId"));
                commentVo1.setIsLike(redisUtil.sHasKey("post:" + commentVo1.getArticleId() + ":comment:" + commentVo1.getId() + ":likeListId",commentVo1.getUserId()));
//                commentVo1.setCommentNum(redisUtil.sGetSetSize("post:" + commentVo1.getArticleId() + ":comment:" + commentVo1.getId()));
                commentVo1.setLikeListId(redisUtil.sGet("post:" + commentVo1.getArticleId() + ":comment:" + commentVo1.getId() + ":likeListId"));
            }
            commentVo.setReply(commentVos1);
        }
        return commentVos;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("userId", comment.getUserId());
        example.setOrderByClause("id desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        return comments.get(0);
    }

}
