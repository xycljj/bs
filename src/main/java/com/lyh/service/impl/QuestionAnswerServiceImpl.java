package com.lyh.service.impl;

import com.lyh.dao.*;
import com.lyh.entity.*;
import com.lyh.entity.vo.QaReplyVo;
import com.lyh.entity.vo.QaVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.QuestionAnswerService;
import com.lyh.utils.RedisUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionReplyServiceImpl
 * @createTime 2022/4/27
 */
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QaReplyMappers qaReplyMappers;

    @Resource
    private UserFocusMapper userFocusMapper;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    public boolean addReply(QuestionAnswer questionAnswer) {
        questionAnswer.setCreateTime(new Date());
        int i = questionAnswerMapper.insertSelective(questionAnswer);
      /*  Example example = new Example(QuestionAnswer.class);
        example.createCriteria().andEqualTo("fromUserId",questionAnswer.getFromUserId());
        example.setOrderByClause("id desc");
        List<QuestionAnswer> questionAnswers = questionAnswerMapper.selectByExample(example);
        QuestionAnswer questionAnswer1 = questionAnswers.get(0);
        questionAnswer.setCreateTime(new Date());*/
        return i == 1;
    }

    @Override
    public List<QaVo> findQuestionAnswerList(Long questionId, Long userId) {
        List<QaVo> list = new ArrayList<>();
        Example example = new Example(QuestionAnswer.class);
        example.createCriteria().andEqualTo("questionId", questionId);
        List<QuestionAnswer> questionAnswers = questionAnswerMapper.selectByExample(example);
        for (QuestionAnswer questionAnswer : questionAnswers) {
            QaVo qaVo = new QaVo();
            qaVo.setQuestionAnswer(questionAnswer);
            Long fromUserId = questionAnswer.getFromUserId();
            User user = userMapper.selectByPrimaryKey(fromUserId);
            qaVo.setUsername(user.getUsername());
            qaVo.setAvatar(user.getAvatar());
            // 用户是否点赞有用 (查询redis中list是否存在该用户id)
            if (userId == null) {//用户未登录
                qaVo.setIsUseful(false);
                qaVo.setIsFocus(false);
            } else {//用户已登录
                qaVo.setIsUseful(redisUtil.sHasKey("qa" + questionAnswer.getId() + "usefulList", userId));
                // 查询回答用户是否被登录用户关注
                Example example1 = new Example(UserFocus.class);
                example1.createCriteria().andEqualTo("userId", userId).andEqualTo("authorId", questionAnswer.getFromUserId());
                List<UserFocus> userFoci = userFocusMapper.selectByExample(example1);
                qaVo.setIsFocus(userFoci.size() > 0);
            }
            // "有用"个数
            qaVo.setUseful(redisUtil.sGetSetSize("qa" + questionAnswer.getId() + "usefulList"));
            // 查询回答的评论
            List<QaReplyVo> qaReplies =qaReplyMappers.selectQaVo(questionAnswer.getId());
            qaVo.setQaReplyList(qaReplies);
            qaVo.setQaReplyNum(qaReplies.size());
            Question question = questionMapper.selectByPrimaryKey(questionId);
            qaVo.setQuestionerThinkUseful(redisUtil.sHasKey("qa"+questionAnswer.getId()+"usefulList",question.getUserId()));
            Example example1 = new Example(QaReply.class);
            example1.createCriteria().andEqualTo("qaId",questionAnswer.getId())
                    .andEqualTo("userId",question.getUserId()).andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
            qaVo.setFeedBack(qaReplyMappers.selectCountByExample(example1));
            list.add(qaVo);
        }
        return list;
    }

    @Override
    public void useful(Long qaId, Long userId) {
        redisUtil.sAdd("qa" + qaId + "usefulList", userId);
    }

    @Override
    public void cancelUseful(Long qaId, Long userId) {
        redisUtil.srem("qa" + qaId + "usefulList", userId);
    }

    @Override
    public boolean focus(Long answerUserId, Long loginUserId) {
        UserFocus userFocus = new UserFocus();
        userFocus.setUserId(loginUserId);
        userFocus.setAuthorId(answerUserId);
        int insert = userFocusMapper.insert(userFocus);
        return insert == 1;
    }

    @Override
    public boolean unfocus(Long answerUserId, Long loginUserId) {
        Example example = new Example(UserFocus.class);
        example.createCriteria().andEqualTo("userId",loginUserId).andEqualTo("authorId",answerUserId);
        int i = userFocusMapper.deleteByExample(example);
        return i == 1;
    }
}
