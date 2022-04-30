package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.QaReply;
import com.lyh.entity.vo.QaReplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QaReplyMappers extends BaseMapper<QaReply> {

    List<QaReplyVo> selectQaVo(@Param("qaId") Long id);

    QaReplyVo selectRecentlyComment(@Param("userId") Long userId);
}