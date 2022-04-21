package com.lyh.entity.vo;

import com.lyh.entity.MsgInfo;
import lombok.Data;

/**
 * @author lyh
 * @ClassName MsgInfoVo
 * @createTime 2022/4/21
 */
@Data
public class MsgInfoVo extends MsgInfo {

    /**
     * 发送者头像
     */
    private String fromAvatar;

    /**
     * 接收者头像
     */
    private String toAvatar;
}
