package com.lyh.service.impl;

import com.lyh.dao.SkillFieldMapper;
import com.lyh.entity.SkillField;
import com.lyh.service.SkillFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyh
 * @ClassName SkillFieldServiceImpl
 * @createTime 2022/5/7
 */
@Service
public class SkillFieldServiceImpl implements SkillFieldService {

    @Resource
    private SkillFieldMapper skillFieldMapper;

    @Override
    public List<SkillField> getList() {
        return skillFieldMapper.selectAll();
    }

    @Override
    public List<String> getListString(String skillField) {
        List<SkillField> skillFields = skillFieldMapper.selectByIds(skillField);
        List<String> list = new ArrayList<>();
        for(SkillField skillField1 : skillFields){
            list.add(skillField1.getValue());
        }
        return list;
    }
}
