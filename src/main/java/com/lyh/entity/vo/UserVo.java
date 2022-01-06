package com.lyh.entity.vo;

import com.lyh.entity.User;
import lombok.Data;

/**
 * @author lyh
 * @ClassName UserVo
 * @createTime 2021/12/28 09:39:00
 */
@Data
public class UserVo{

    private User user;

    private String token;

    private String userType;

    private Boolean isTrue;
}
