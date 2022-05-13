package com.lyh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.Application;
import com.lyh.entity.vo.ApplicationVo;
import com.lyh.service.ApplicationService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lyh
 * @ClassName ApplicationController
 * @createTime 2022/4/30
 */
@RestController
@RequestMapping("application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    /**
     * @return
     * @Author lyh
     * @Description 未审核
     * @Param
     * @Date 2022/4/30
     **/
    @GetMapping("getUnAuditList")
    public Result<PageInfo<ApplicationVo>> getUnAuditList(String username, String startTime, String endTime,
                                                          @RequestParam(defaultValue = "1") Integer pageIndex,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ApplicationVo> pageInfo = applicationService.findAuditList(username, startTime, endTime, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description 已审核
     * @Param
     * @Date 2022/4/30
     **/
    @GetMapping("getApprovList")
    public Result<PageInfo<ApplicationVo>> getApprovList(Long userId, String username, String startTime, String endTime,
                                                         @RequestParam(defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ApplicationVo> pageInfo = applicationService.findApprovList(userId, username, startTime, endTime, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description 流程状态改变（num 为1 指的是通过审批，为2 指的是被驳回）
     * @Param
     * @Date 2022/4/30
     **/
    @Transactional
    @GetMapping("changeStatus")
    public Result<?> updateStatus(Long applicationId, Integer num) {
        applicationService.updateStatus(applicationId, num);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 用户是否已经发起流程
     * @Param
     * @Date 2022/4/30
     **/
    @GetMapping("isUserInProcess")
    public Result<Boolean> isUserInProcess(Long userId) {
        boolean flag = applicationService.findUserInProcess(userId);
        return ResultUtil.ok(flag);
    }
}
