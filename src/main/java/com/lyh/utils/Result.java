package com.lyh.utils;

import lombok.Data;


/**
 * @author lyh
 * @ClassName ResultUtils
 * @createTime 2021/12/16 11:29:00
 */
@Data
public class Result<T> {

    private int code;

    private String message;

    private T data;
}
