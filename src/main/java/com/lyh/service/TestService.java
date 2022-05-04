package com.lyh.service;

import com.lyh.entity.Test;
import com.lyh.entity.vo.TestSubjectVo;
import com.lyh.entity.vo.TestVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName TestService
 * @createTime 2022/5/4
 */
public interface TestService {
    void addTest(TestVo testVo);


    List<Test> findTestList(String name);


    List<TestSubjectVo> findTestItemList(Long id);

    Test findTestById(Long id);

    void delTestPaper(Long testId);
}
