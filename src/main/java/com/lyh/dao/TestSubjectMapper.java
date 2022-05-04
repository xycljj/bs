package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.TestSubject;

public interface TestSubjectMapper extends BaseMapper<TestSubject> {

    Long selectLatestSubjectId();

}