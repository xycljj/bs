package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * skill_field
 * @author 
 */
@Data
public class SkillField implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 类别名称
     */
    private String value;

    private static final long serialVersionUID = 1L;

}