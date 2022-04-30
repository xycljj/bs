package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Application;
import com.lyh.entity.vo.ApplicationVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ApplicationMapper extends BaseMapper<Application> {

    List<ApplicationVo> selectAuditList(@Param("username") String username, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ApplicationVo> selecApprovList(@Param("userId") Long userId, @Param("username") String username, @Param("startTime") String startTime, @Param("endTime") String endTime);
}