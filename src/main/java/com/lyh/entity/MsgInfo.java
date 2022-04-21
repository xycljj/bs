package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class MsgInfo implements Serializable {
    /**
     * 消息id
     */
    @Id
    private Long id;

    /**
     * 消息发送者id
     */
    private Long fromUserId;

    /**
     * 消息发送者名称
     */
    private String fromUsername;

    /**
     * 消息接收者id
     */
    private Long toUserId;

    /**
     * 消息接收者名称
     */
    private String toUsername;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息发送时间
     */
    private Date createTime;

    /**
     * 是否已读（1 已读）
     */
    private Integer unReadFlag;

    private static final long serialVersionUID = 1L;

}