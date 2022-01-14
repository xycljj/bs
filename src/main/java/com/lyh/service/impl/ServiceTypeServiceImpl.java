package com.lyh.service.impl;

import com.lyh.dao.ServiceTypeMapper;
import com.lyh.entity.TypeOfService;
import com.lyh.enums.DelEnum;
import com.lyh.service.ServiceTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lyh
 * @ClassName ServiceTypeServiceImpl
 * @createTime 2021/12/17 10:38:00
 */
@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    @Resource
    private ServiceTypeMapper serviceTypeMapper;

    @Override
    public void addServiceType(TypeOfService serviceType) {
        serviceType.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        serviceType.setCreateTime(new Date());
        serviceTypeMapper.insert(serviceType);
    }
}
