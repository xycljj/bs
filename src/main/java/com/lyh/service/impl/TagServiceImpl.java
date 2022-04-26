package com.lyh.service.impl;

import com.lyh.dao.PTagMapper;
import com.lyh.dao.SubTagMapper;
import com.lyh.entity.PTag;
import com.lyh.entity.SubTag;
import com.lyh.entity.vo.TagVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.TagService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyh
 * @ClassName TagServiceImpl
 * @createTime 2022/4/26
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private SubTagMapper subTagMapper; // 问题2

    @Resource
    private PTagMapper pTagMapper; //问题1

    @Override
    public boolean addTag1(PTag pTag) {
        pTag.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return pTagMapper.insert(pTag) == 1;
    }

    @Override
    public boolean delTag1(Long id) {
        PTag pTag = new PTag();
        pTag.setId(id);
        pTag.setIsDel(DelEnum.IS_DEL.getValue());
        return pTagMapper.updateByPrimaryKeySelective(pTag) == 1;
    }

    @Override
    public List<PTag> findTag1ListByTagName(String tagName) {
        return pTagMapper.selectTag1ListByTagName(tagName);
    }

    @Override
    public boolean addTag2(SubTag subTag) {
        subTag.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return subTagMapper.insert(subTag) == 1;
    }

    @Override
    public boolean delTag2(Long id) {
        SubTag subTag = new SubTag();
        subTag.setId(id);
        subTag.setIsDel(DelEnum.IS_DEL.getValue());
        return subTagMapper.updateByPrimaryKeySelective(subTag) == 1;
    }

    @Override
    public List<TagVo> findTag2ListByTagName(String tagName) {
        List<TagVo> list = new ArrayList<>();
        List<PTag> pTags = pTagMapper.selectTag1ListByTagName(tagName);
        for(PTag pTag : pTags){
            TagVo tagVo = new TagVo();
            tagVo.setPTagId(pTag.getId());
            tagVo.setPTagName(pTag.getName());
            Example example1 = new Example(SubTag.class);
            example1.createCriteria().andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue())
            .andEqualTo("ptagId",pTag.getId());
            tagVo.setSubTagList(subTagMapper.selectByExample(example1));
            list.add(tagVo);
        }
        return list;
    }

    @Override
    public boolean checkTagName1IsExist(String name) {
        Example example = new Example(PTag.class);
        example.createCriteria().andEqualTo("name",name).andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        List<PTag> pTags = pTagMapper.selectByExample(example);
        return pTags.size() > 0;
    }

    @Override
    public boolean checkTagName2IsExist(String name) {
        Example example = new Example(SubTag.class);
        example.createCriteria().andEqualTo("name",name);
        List<SubTag> subTags = subTagMapper.selectByExample(example);
        return subTags.size() > 0;
    }

    @Override
    public List<SubTag> findSubTagList() {
        Example example = new Example(SubTag.class);
        example.createCriteria().andEqualTo("isDel",DelEnum.IS_NOT_DEL.getValue());
        List<SubTag> subTags = subTagMapper.selectByExample(example);
        return subTags;
    }
}
