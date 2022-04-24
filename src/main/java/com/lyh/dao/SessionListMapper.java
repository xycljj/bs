package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.SessionList;
import com.lyh.entity.vo.SessionListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SessionListMapper extends BaseMapper<SessionList> {


    void addUnReadCount(@Param("userId") Long userId, @Param("toUserId") Long toUserId, @Param("count") Integer count);

    void delUnReadCount(@Param("fromUserId") Long fromUserId,@Param("toUserId") Long toUserId);

    Long selectIdByUser(@Param("fromId") Long fromId, @Param("toId") Long toId);

    List<SessionListVo> selectByUserId(@Param("id") Long id);

    List<Long> selectUserIdByUserId(@Param("id") Long id);

    Integer countUnReadMsg(@Param("toUserId") Long toUserId,@Param("userId") Long userId);

}