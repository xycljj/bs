package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SessionList implements Serializable {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 所属用户
     */
    private Long toUserId;

    /**
     * 会话名称
     */
    private String listName;

    /**
     * 未读消息数
     */
    private Integer unReadCount;

    private static final long serialVersionUID = 1L;
}