package com.lyh.service;

import com.lyh.entity.MsgInfo;
import com.lyh.entity.vo.MsgInfoVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName MsgInfoService
 * @createTime 2022/4/21
 */
public interface MsgInfoService {

    List<MsgInfoVo> selectMsgList(Long fromUserId, Long toUserId);

    void msgRead(Long fromUserId, Long toUserId);
}
