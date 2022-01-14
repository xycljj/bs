package com.lyh.controller;

import com.lyh.entity.TypeOfService;
import com.lyh.service.ServiceTypeService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName TypeOfServiceController
 * @createTime 2022/01/14 16:49:00
 */

@RestController
@Slf4j
@RequestMapping("serviceType")
public class TypeOfServiceController {

    @Resource
    private ServiceTypeService serviceTypeService;

    @PostMapping("addServiceType")
    public Result<Boolean> addServiceType(@RequestBody TypeOfService typeOfService){
        serviceTypeService.addServiceType(typeOfService);
        return ResultUtil.ok(true);
    }
}
