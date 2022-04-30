package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.vo.ApplicationVo;

import java.util.Date;

/**
 * @author lyh
 * @ClassName ApplicationService
 * @createTime 2022/4/30
 */
public interface ApplicationService {

    /**
    * @return
    * @Author lyh
    * @Description 新增申请
    * @Param
    * @Date 2022/4/30
    **/
    void addApplication(String toString, Long userId);

    PageInfo<ApplicationVo> findAuditList(String username, String startTime, String endTime, Integer pageIndex, Integer pageSize);

    PageInfo<ApplicationVo> findApprovList(Long userId, String username, String startTime, String endTime, Integer pageIndex, Integer pageSize);

    void updateStatus(Long applicationId, Integer num);

    boolean findUserInProcess(Long userId);
}
