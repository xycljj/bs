package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * user_focus
 * @author 
 */
@Data
public class UserFocus implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 作者id(同为userId)
     */
    private Long authorId;

    private static final long serialVersionUID = 1L;


}