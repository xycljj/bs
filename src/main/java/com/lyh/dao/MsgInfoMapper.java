package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.MsgInfo;
import com.lyh.entity.vo.MsgInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgInfoMapper extends BaseMapper<MsgInfo> {

    void msgRead(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    List<MsgInfo> selectMsgList(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);
}
