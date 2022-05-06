package com.lyh.entity.vo;

import com.lyh.entity.UserTestScore;
import lombok.Data;

/**
 * @author lyh
 * @ClassName UserTestScoreVo
 * @createTime 2022/5/5
 */
@Data
public class UserTestScoreVo extends UserTestScore {

    private String username;

    private String testTitle;

    private String testCover;
}
