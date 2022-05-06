package com.lyh.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName TestScoreRulesVo
 * @createTime 2022/5/6
 */
@Data
public class TestScoreRulesVo {

    private List<Integer> scoreSegment;

    private String result;

}
