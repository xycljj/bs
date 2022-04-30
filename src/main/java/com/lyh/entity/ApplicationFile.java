package com.lyh.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * application_file
 * @author 
 */
@Data
public class ApplicationFile implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户上传的认证文件地址
     */
    private String url;

    private static final long serialVersionUID = 1L;

}