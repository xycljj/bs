package com.lyh.entity.vo;

import com.lyh.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName UserInfo
 * @createTime 2022/5/7
 */
@Data
public class UserInfo extends User {

    private List<String> skills;

}
