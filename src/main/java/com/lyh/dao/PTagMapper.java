package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.PTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PTagMapper extends BaseMapper<PTag> {

    List<PTag> selectTag1ListByTagName(@Param("tagName") String tagName);
}