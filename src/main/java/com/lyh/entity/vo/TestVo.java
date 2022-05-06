package com.lyh.entity.vo;

import com.lyh.entity.TestSubject;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName TestVo
 * @createTime 2022/5/4
 */
@Data
public class TestVo {

    private String name;

    private List<TestSubjectVo> testSubjects;

    private List<TestScoreRulesVo> resultList;

    private Date date;

    private String cover;

}
