package com.lyh.service;

import com.lyh.entity.UserFocus;

/**
 * @author lyh
 * @ClassName UserFocusService
 * @createTime 2022/4/14
 */
public interface UserFocusService {

    boolean focus(UserFocus userFocus);

    boolean cancelFocus(UserFocus userFocus);

    boolean isFocused(UserFocus userFocus);
}
