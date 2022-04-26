package com.lyh.entity.vo;

import com.lyh.entity.SubTag;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName TagVo
 * @createTime 2022/4/26
 */
@Data
public class TagVo {

    private Long pTagId;

    private String pTagName;

    private List<SubTag> subTagList;



}
