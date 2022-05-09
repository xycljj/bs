package com.lyh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.dao.ApplicationFileMapper;
import com.lyh.dao.ApplicationMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Application;
import com.lyh.entity.ApplicationFile;
import com.lyh.entity.User;
import com.lyh.entity.vo.ApplicationVo;
import com.lyh.service.ApplicationService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName ApplicationServiceImpl
 * @createTime 2022/4/30
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private ApplicationFileMapper applicationFileMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void addApplication(String toString, Long userId, String skill) {
        Application application = new Application();
        application.setIsPass(0);
        application.setUrlIds(toString);
        application.setUserId(userId);
        application.setSkillField(skill);
        application.setCreateTime(new Date());
        applicationMapper.insert(application);
    }

    @Override
    public PageInfo<ApplicationVo> findAuditList(String username, String startTime, String endTime, Integer pageIndex, Integer pageSize) {
        if (startTime != null && endTime != null) {
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
        }
        Page<ApplicationVo> applicationVos = PageHelper.startPage(pageIndex, pageSize);
        List<ApplicationVo> list = applicationMapper.selectAuditList(username, startTime, endTime);
        for (ApplicationVo applicationVo : list) {
            List<ApplicationFile> applicationFiles = applicationFileMapper.selectByIds(applicationVo.getUrlIds());
            List<String> urls = new ArrayList<>();
            for (ApplicationFile applicationFile : applicationFiles) {
                urls.add(applicationFile.getUrl());
            }
            applicationVo.setUrls(urls);
        }
        PageInfo<ApplicationVo> pageInfo = applicationVos.toPageInfo();
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public PageInfo<ApplicationVo> findApprovList(Long userId, String username, String startTime, String endTime, Integer pageIndex, Integer pageSize) {
        if (startTime != null && endTime != null) {
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
        }
        Page<ApplicationVo> applicationVos = PageHelper.startPage(pageIndex, pageSize);
        List<ApplicationVo> list = applicationMapper.selecApprovList(userId, username, startTime, endTime);
        for (ApplicationVo applicationVo : list) {
            List<ApplicationFile> applicationFiles = applicationFileMapper.selectByIds(applicationVo.getUrlIds());
            List<String> urls = new ArrayList<>();
            for (ApplicationFile applicationFile : applicationFiles) {
                urls.add(applicationFile.getUrl());
            }
            applicationVo.setUrls(urls);
        }
        PageInfo<ApplicationVo> pageInfo = applicationVos.toPageInfo();
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public void updateStatus(Long applicationId, Integer num) {
        Application application = new ApplicationVo();
        application.setId(applicationId);
        application.setIsPass(num);
        applicationMapper.updateByPrimaryKeySelective(application);
        Application application1 = applicationMapper.selectByPrimaryKey(applicationId);
        if(num == 1){
            User user = new User();
            user.setId(application1.getUserId());
            user.setSkillField(application1.getSkillField());
            user.setIsConsultant(1);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public boolean findUserInProcess(Long userId) {
        Example example = new Example(Application.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isPass", 0);
        List<Application> applications = applicationMapper.selectByExample(example);
        return applications.size() > 0;
    }
}
