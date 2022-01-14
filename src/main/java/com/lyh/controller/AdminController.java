package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.Admin;
import com.lyh.service.AdminService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName AdminController
 * @createTime 2021/12/17 10:39:00
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * @Author lyh
     * @Description //TODO 添加管理员
     * @Param
     * @return
     * @Date 2021/12/17
     **/
    @PostMapping("addAdmin")
    public Result<Boolean> addAdmin(@RequestBody Admin admin){
        if(adminService.addAdmin(admin)==1){
            return ResultUtil.ok(true);
        }
        return ResultUtil.fail("注册失败");
    }

    /**
     * @Author lyh
     * @Description //TODO 查询管理员列表
     * @Param
     * @return
     * @Date 2021/12/17
     **/
    @GetMapping("findAdminList")
    public Result<PageInfo<Admin>> findUserList(String searchStr, @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize){
        if(StringUtils.isBlank(searchStr)){
            searchStr = null;
        }
        Page<Admin> page = PageHelper.startPage(pageIndex,pageSize);
        List<Admin> adminList = adminService.findAdminList(searchStr);
        PageInfo<Admin> pageInfo = page.toPageInfo();
        pageInfo.setList(adminList);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @Author lyh
     * @Description //TODO 查看管理员详情
     * @Param
     * @return
     * @Date 2021/12/17
     **/
    @GetMapping("findAdminById")
    public Result<Admin> findAdminById(Long id){
        Admin admin = adminService.findAdminById(id);
        return ResultUtil.ok(admin);
    }


}
