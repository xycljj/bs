package com.lyh.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName TestSubjectVo
 * @createTime 2022/5/4
 */
@Data
public class TestSubjectVo {

    private String title;

    private String type;

    private List<String> textList;

    private List<Integer> scoreList;

    private Long id;

    private Long score;
}
