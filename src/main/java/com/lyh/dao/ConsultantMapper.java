package com.lyh.dao;

import com.lyh.entity.Consultant;

public interface ConsultantMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Consultant record);

    int insertSelective(Consultant record);

    Consultant selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Consultant record);

    int updateByPrimaryKey(Consultant record);
}