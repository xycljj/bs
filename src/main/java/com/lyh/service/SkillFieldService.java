package com.lyh.service;

import com.lyh.entity.SkillField;

import java.util.List;

/**
 * @author lyh
 * @ClassName SkillFieldService
 * @createTime 2022/5/7
 */
public interface SkillFieldService {
    List<SkillField> getList();

    List<String> getListString(String skillField);
}
