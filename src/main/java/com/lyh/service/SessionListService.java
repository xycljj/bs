package com.lyh.service;

import com.lyh.entity.SessionList;
import com.lyh.entity.vo.SessionListVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName SessionListService
 * @createTime 2022/4/21
 */
public interface SessionListService {
    List<SessionListVo> selectByUserId(Long id);

    List<Long> selectUserIdByUserId(Long id);

    void createSession(SessionList sessionList);

    Long selectIdByUser(Long toUserId, Long id);

    void delSession(Long sessionId);

    SessionList findSessionListById(Long sessionId);

    void delUnReadCount(Long fromUserId, Long toUserId);

    Integer findUnReadMsgCountByUserId(Long userId);
}
