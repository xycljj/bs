package com.lyh.controller;

import com.lyh.entity.Consultant;
import com.lyh.entity.User;
import com.lyh.service.ConsultantService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName ConsultantController
 * @createTime 2021/12/17 11:27:00
 */
@RestController
@RequestMapping("consultant")
public class ConsultantController {

    @Resource
    ConsultantService consultantService;


    /**
     * @Author lyh
     * @Description //添加心理咨询师
     * @Param consultant 心理咨询师
     * @return
     * @Date 2021/12/22
     **/
    @PostMapping("addConsultant")
    public Result<Consultant> addConsultant(@RequestBody Consultant consultant){
        if(consultantService.addConsultant(consultant)==1){
            return ResultUtil.ok(consultant);
        }
        return ResultUtil.ok();
    }


    /**
     * @Author lyh
     * @Description //删除心理咨询师
     * @Param id 主键id
     * @return
     * @Date 2021/12/22
     **/
    @GetMapping("del")
    public Result<Boolean> delConsultant(Long id){
        if(consultantService.delConsultant(id)==1){
            return ResultUtil.ok(true);
        }
        return ResultUtil.ok(false);
    }

    /**
     * @Author lyh
     * @Description //查询心理咨询师个人信息
     * @Param id 主键id
     * @return
     * @Date 2021/12/22
     **/
    @GetMapping("findDetail")
    public Result<Consultant> findDetailById(Long id){
        Consultant consultant = consultantService.findDetailById(id);
        return ResultUtil.ok(consultant);
    }

    /**
     * @Author lyh
     * @Description //编辑/更新心理咨询师信息
     * @Param
     * @return
     * @Date 2021/12/22
     **/
    @PostMapping("edit")
    public Result<Consultant> editConsultant(@RequestBody Consultant consultant){
        Consultant consultantInfo = consultantService.editConsultant(consultant);
        return ResultUtil.ok(consultantInfo);
    }

}
