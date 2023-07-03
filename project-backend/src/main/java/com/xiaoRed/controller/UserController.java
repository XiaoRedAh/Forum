package com.xiaoRed.controller;

import com.xiaoRed.entity.RestBean;
import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.entity.user.AccountUser;
import com.xiaoRed.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    UserService userService;
    /**
     * prehandle()方法将登录成功的用户详细信息存放在请求体的session中
     * 调用这个接口时，从请求体中拿到用户详细信息
     * 然后直接将它返回给前端，前端就可以很方便地操纵这些信息了
     */
    @GetMapping("/me")
    public RestBean<AccountUser> me(@SessionAttribute("accountUser") AccountUser accountUser){
        return RestBean.success(accountUser);
    }

    @PostMapping("/save-info")
    public RestBean<String> saveInfo(@RequestBody @Validated AccountInfo accountInfo,
                                   @SessionAttribute("accountUser") AccountUser accountUser){
        accountInfo.setUid(accountUser.getId());//将uid给到用户信息
        if(userService.saveUserInfo(accountInfo)){
            accountUser.setUsername(accountInfo.getUsername());//将Session中存的用户名也要改掉
            return RestBean.success();
        }else{
            return RestBean.failure(400,"用户名已存在，无法修改");
        }
    }

    @GetMapping("/info")
    public RestBean<AccountInfo> info(@SessionAttribute("accountUser") AccountUser accountUser){
        return RestBean.success(userService.userInfo(accountUser.getId()));
    }
}
