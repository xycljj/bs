package com.lyh.service;

import com.lyh.entity.PTag;
import com.lyh.entity.SubTag;
import com.lyh.entity.vo.TagVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName TagService
 * @createTime 2022/4/26
 */
public interface TagService {

    boolean addTag1(PTag pTag);

    boolean delTag1(Long id);

    List<PTag> findTag1ListByTagName(String tagName);

    boolean addTag2(SubTag subTag);

    boolean delTag2(Long id);

    List<TagVo> findTag2ListByTagName(String tagName);

    boolean checkTagName1IsExist(String name);

    boolean checkTagName2IsExist(String name);

    List<SubTag> findSubTagList();

}
