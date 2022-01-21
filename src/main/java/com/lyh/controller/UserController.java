package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lyh.entity.User;
import com.lyh.entity.vo.UserVo;
import com.lyh.service.UserService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import com.lyh.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lyh
 * @ClassName UserController
 * @createTime 2021/12/16 10:55:00
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * @return
     * @Author lyh
     * @Description //TODO
     * @Param
     * @Date 2021/12/24
     **/
    @PostMapping("login")
    public Result<UserVo> login(@RequestBody User user) {
        User user1 = userService.login(user);
        if (user1 != null) {
            UserVo userVo = new UserVo();
            String token = TokenUtils.token(user1.getId());
            userVo.setUser(user1);
            userVo.setToken(token);
            userVo.setUserType(user1.getUserType() == 2 ? "admin" : "user");
            System.out.println("登录成功！ 用户名：" + user1.getUsername());
            return ResultUtil.ok(userVo);
        }
        return ResultUtil.fail("用户名密码错误");

    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 注册
     * @Param
     * @Date 2021/12/16
     **/
    @PostMapping("addUser")
    public Result<User> addUser(@RequestBody User user) {
        if (userService.addUser(user) == 1) {
            return ResultUtil.ok(user);
        }
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 删除
     * @Param
     * @Date 2021/12/17
     **/
    @GetMapping("delUser")
    public Result<Boolean> delUser(Long id) {
//        Long _id = Long.parseLong(id);
        if (userService.delUser(id) == 1) {
            return ResultUtil.ok(true);
        }
        return ResultUtil.fail("删除失败");
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 编辑/更新
     * @Param
     * @Date 2021/12/17
     **/
    @PostMapping("edit")
    public Result<User> editUser(@RequestBody User user) {
        User userInfo = userService.editUser(user);
        return ResultUtil.ok(userInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 查询所有用户
     * @Param
     * @Date 2021/12/16
     **/
    @GetMapping("findUserList")
    public Result<PageInfo<User>> findUserList(String username, String phone, Integer userType,
                                               @RequestParam(defaultValue = "1") int pageIndex,
                                               @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> page = PageHelper.startPage(pageIndex, pageSize);
        List<User> userList = userService.findUserList(username, phone, userType);
        PageInfo<User> pageInfo = page.toPageInfo();
        pageInfo.setList(userList);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 查个人详情
     * @Param
     * @Date 2021/12/16
     **/
    @GetMapping("findUserById")
    public Result<User> findUserById(Long id) {
        User user = userService.findUserById(id);
        return ResultUtil.ok(user);
    }

    /**
     * @return Result<Boolean> true 表示用户名已存在，false表示用户名未被注册
     * @Author lyh
     * @Description //TODO 查询用户名是否存在
     * @Param username 用户名
     * @Date 2021/12/17
     **/
    @GetMapping("findUserByUsername")
    public Result<Boolean> findUserByUsername(String username) {
        if (StringUtil.isEmpty(username)) {
            return ResultUtil.ok("未输入用户名", false);
        }
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return ResultUtil.ok(false);
        }
        return ResultUtil.ok(true);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO info 验证token
     * @Param
     * @Date 2021/12/28
     **/
    @GetMapping("info")
    public Result<UserVo> userInfo(String token) {
        UserVo userVo = new UserVo();
        userVo.setIsTrue(TokenUtils.verify(token));
        if (!userVo.getIsTrue()) {
            return ResultUtil.fail("离开页面太久，登录已失效....");
        }
        Long uid = TokenUtils.getIdFromToken(token);
        User user = userService.findUserById(uid);
        userVo.setUser(user);
        return ResultUtil.ok(userVo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 退出登录
     * @Param
     * @Date 2021/12/29
     **/
    @PostMapping("logout")
    public Result<String> logout() {
        System.out.println("退出登录");
        return ResultUtil.ok("退出登录");
    }
}
