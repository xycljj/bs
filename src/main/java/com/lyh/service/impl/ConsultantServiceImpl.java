package com.lyh.service.impl;

import com.lyh.dao.ConsultantMapper;
import com.lyh.entity.Consultant;
import com.lyh.enums.DelEnum;
import com.lyh.service.ConsultantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName ConsultantServiceImpl
 * @createTime 2021/12/17 10:38:00
 */
@Service
public class ConsultantServiceImpl implements ConsultantService {
    @Resource
    private ConsultantMapper consultantMapper;

    @Override
    public int addConsultant(Consultant consultant) {
        return consultantMapper.insertSelective(consultant);
    }

    @Override
    public int delConsultant(Long id) {
        Consultant consultant = consultantMapper.selectByPrimaryKey(id);
        consultant.setIsDel(DelEnum.IS_DEL.getValue());
        return consultantMapper.updateByPrimaryKeySelective(consultant);
    }

    @Override
    public Consultant findDetailById(Long id) {
        return consultantMapper.selectByPrimaryKey(id);
    }

    @Override
    public Consultant editConsultant(Consultant consultant) {
        consultantMapper.updateByPrimaryKeySelective(consultant);
        return consultant;
    }
}
