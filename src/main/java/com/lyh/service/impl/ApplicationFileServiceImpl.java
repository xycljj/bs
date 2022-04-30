package com.lyh.service.impl;

import com.lyh.dao.ApplicationFileMapper;
import com.lyh.entity.ApplicationFile;
import com.lyh.service.ApplicationFileService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName ApplicationFileServiceImpl
 * @createTime 2022/4/30
 */
@Service
public class ApplicationFileServiceImpl implements ApplicationFileService {
    @Resource
    private ApplicationFileMapper applicationFileMapper;

    @Override
    public Long addFileUrl(String url) {
        ApplicationFile applicationFile = new ApplicationFile();
        applicationFile.setUrl(url);
        applicationFileMapper.insert(applicationFile);
        Example example = new Example(ApplicationFile.class);
        example.createCriteria().andEqualTo("url",url);
        ApplicationFile applicationFile1 = applicationFileMapper.selectOneByExample(example);
        return applicationFile1.getId();
    }
}
