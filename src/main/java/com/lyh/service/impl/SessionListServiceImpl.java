package com.lyh.service.impl;

import com.lyh.dao.SessionListMapper;
import com.lyh.entity.SessionList;
import com.lyh.entity.vo.SessionListVo;
import com.lyh.service.SessionListService;
import com.lyh.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyh
 * @ClassName SessionListServiceImpl
 * @createTime 2022/4/21
 */
@Service
public class SessionListServiceImpl implements SessionListService {

    @Resource
    private SessionListMapper sessionListMapper;

    @Resource
    private UserService userService;

    @Override
    public List<SessionListVo> selectByUserId(Long id) {
        return sessionListMapper.selectByUserId(id);
    }

    @Override
    public List<Long> selectUserIdByUserId(Long id) {
        return sessionListMapper.selectUserIdByUserId(id);
    }

    @Override
    public void createSession(SessionList sessionList) {
        sessionListMapper.insert(sessionList);
    }

    @Override
    public Long selectIdByUser(Long toUserId, Long id) {
        return sessionListMapper.selectIdByUser(toUserId, id);
    }

    @Override
    public void delSession(Long sessionId) {
        sessionListMapper.deleteByPrimaryKey(sessionId);
    }

    @Override
    public SessionList findSessionListById(Long sessionId) {
        return sessionListMapper.selectByPrimaryKey(sessionId);
    }

    @Override
    public void delUnReadCount(Long fromUserId, Long toUserId) {
        sessionListMapper.delUnReadCount(fromUserId, toUserId);
    }

    @Override
    public Integer findUnReadMsgCountByUserId(Long userId) {
        Example example = new Example(SessionList.class);
        example.createCriteria().andEqualTo("toUserId",userId);
        List<SessionList> sessionLists = sessionListMapper.selectByExample(example);
        Integer count = 0;
        for(SessionList sessionList : sessionLists){
            count += sessionList.getUnReadCount();
        }
        return count;
    }
}
