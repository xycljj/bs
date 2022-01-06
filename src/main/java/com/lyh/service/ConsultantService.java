package com.lyh.service;

import com.lyh.entity.Consultant;

/**
 * @author lyh
 * @ClassName ConsultantService
 * @createTime 2021/12/17 10:37:00
 */
public interface ConsultantService {
    int addConsultant(Consultant consultant);

    int delConsultant(Long id);

    Consultant findDetailById(Long id);

    Consultant editConsultant(Consultant consultant);
}
