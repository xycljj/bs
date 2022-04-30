package com.lyh.entity.vo;

import com.lyh.entity.Application;
import lombok.Data;

import java.util.List;

/**
 * @author lyh
 * @ClassName ApplicationVo
 * @createTime 2022/4/30
 */
@Data
public class ApplicationVo extends Application {

    /**
     * 用户名
     */
    private String username;

    private List<String> urls;
}
