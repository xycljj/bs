package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.UserTestScore;
import com.lyh.entity.vo.UserTestScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTestScoreMapper extends BaseMapper<UserTestScore> {

    List<UserTestScoreVo> selectByUserIdOrTestId(@Param("userId") Long userId, @Param("testId") Long testId);

}