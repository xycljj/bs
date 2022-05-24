package com.lyh.utils;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.vo.PageVo;
import com.lyh.enums.ResultCodeEnum;


/**
 * @author lyh
 * @ClassName ResultUtils
 * @createTime 2021/12/16 14:24:00
 */
public class ResultUtil {

    private static final String SUCCESS_MESSAGE = "success";

    private static final String FAIL_MESSAGE = "fail";

    public static <T> Result<T> ok() {
        return ResultUtil.ok(SUCCESS_MESSAGE, null);
    }

    public static <T> Result<T> ok(T data) {
        return ResultUtil.ok(SUCCESS_MESSAGE, data);
    }

    public static <T> Result<PageInfo<T>> ok(PageInfo<T> data) {
        return ResultUtil.ok(SUCCESS_MESSAGE, PageVo.buildFromPageInfo(data));

    }


    public static <T> Result<T> ok(String message, T data) {

        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getValue());
        result.setMessage(message);
        result.setData(data);
        return result;
    }


    public static <T> Result<T> fail() {
        return ResultUtil.fail(FAIL_MESSAGE, null);
    }


    public static <T> Result<T> fail(String message) {
        return ResultUtil.fail(message, null);
    }

    public static <T> Result<T> fail(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.FAIL.getValue());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
