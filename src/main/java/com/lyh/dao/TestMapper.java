package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper extends BaseMapper<Test> {

    List<Test> selectTestListByName(@Param("name") String name);
}