package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.Admin;
import com.lyh.entity.User;
import com.lyh.entity.vo.AdminVO;
import com.lyh.entity.vo.UserVo;
import com.lyh.service.AdminService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import com.lyh.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.UsesSunMisc;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author lyh
 * @ClassName AdminController
 * @createTime 2021/12/17 10:39:00
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;


    /**
     * @Author lyh
     * @Description // 管理员登录
     * @Param
     * @return
     * @Date 2022/1/19
     **/
    @PostMapping("login")
    public Result<AdminVO> adminLogin(@RequestBody Admin admin, HttpSession session) {
        Admin admin1 = adminService.login(admin);
        if (admin1 != null) {
            AdminVO adminVO = new AdminVO();
            String token = TokenUtils.token(admin1.getId());
            adminVO.setAdmin(admin1);
            adminVO.setToken(token);
            log.info("------------管理员登录-------------");
            session.setAttribute("admin",admin);
            return ResultUtil.ok(adminVO);
        }
        return ResultUtil.fail("用户名密码错误");
    }

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

    /**
     * @return
     * @Author lyh
     * @Description //TODO info 验证token
     * @Param
     * @Date 2021/12/28
     **/
    @GetMapping("info")
    public Result<AdminVO> userInfo(String token) {
        AdminVO adminVO = new AdminVO();
        adminVO.setIsTrue(TokenUtils.verify(token));
        if (!adminVO.getIsTrue()) {
            return ResultUtil.fail("离开页面太久，登录已失效....");
        }
        Long adminId = TokenUtils.getIdFromToken(token);
        Admin admin = adminService.findAdminById(adminId);
        adminVO.setAdmin(admin);
        return ResultUtil.ok(adminVO);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 退出登录
     * @Param
     * @Date 2021/12/29
     **/
    @PostMapping("logout")
    public Result<String> logout(HttpSession session) {
        System.out.println("退出登录");
        session.removeAttribute("admin");
        return ResultUtil.ok("退出登录");
    }

}
