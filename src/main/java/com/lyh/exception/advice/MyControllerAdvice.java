package com.lyh.exception.advice;

import com.lyh.exception.RrException;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyh
 * @ClassName MyControllerAdvice
 * @createTime 2022/5/12
 */
@ControllerAdvice
public class MyControllerAdvice {
    @ResponseBody
    @ExceptionHandler(value = RrException.class)
    public Result errorHandler(RrException ex) {
        return ResultUtil.fail(ex.getMessage());
    }

}
