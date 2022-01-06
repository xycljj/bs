package com.lyh.dao;

import com.lyh.entity.ServiceType;

public interface ServiceTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServiceType record);

    int insertSelective(ServiceType record);

    ServiceType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServiceType record);

    int updateByPrimaryKey(ServiceType record);
}