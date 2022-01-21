package com.lyh.entity.vo;

import com.lyh.entity.Admin;
import lombok.Data;

/**
 * @author lyh
 * @ClassName AdminVO
 * @createTime 2022/01/19 17:22:00
 */
@Data
public class AdminVO {

    private Admin admin;

    private String token;

    /**
     * 判断token是否失效
     */
    private Boolean isTrue;
}
