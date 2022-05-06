package com.lyh.entity.vo;

import com.lyh.entity.TestScoreRule;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName PaperDetail
 * @createTime 2022/5/4
 */
@Data
public class PaperDetail {

    private List<TestSubjectVo> textList;

    private List<TestScoreRulesVo> resultList;

    private String paperName;
}
