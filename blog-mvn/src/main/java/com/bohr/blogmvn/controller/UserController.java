package com.bohr.blogmvn.controller;

import com.bohr.blogmvn.common.entity.Result;
import com.bohr.blogmvn.common.entity.ResultCode;
import com.bohr.blogmvn.entity.User;
import com.bohr.blogmvn.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 用户控制器
 * @author Bohr Fu
 * @date 2019/12/10 17:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户列表
     * @return
     */
    @GetMapping(value = "", produces="application/json;charset=UTF-8")
    public Result listUsers(){
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    /**
     * 根据id获取单个用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable("id")Long id){
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        User user = userService.getUserById(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(user);
        return r;
    }

    /**
     * 创建用户
     * @param user
     * @return
     */
    @PostMapping("/create")
    public Result saveUser(@RequestBody User user){
        Long userId = userService.saveUser(user);
        Result result = Result.success();
        result.simple().put("userId",userId);
        return result;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        Result r = new Result();

        if (null == user.getId()) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
            return r;
        }

        Long userId = userService.updateUser(user);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("userId", userId);
        return r;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Result deleteUserById(@PathVariable("id") Long id) {
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        userService.deleteUserById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

}
