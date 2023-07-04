package com.xiaoRed.controller;

import com.xiaoRed.entity.RestBean;
import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.entity.user.AccountUser;
import com.xiaoRed.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    //邮件地址的正则表达式
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
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

    @PostMapping("/save-email")
    public RestBean<String> saveEmail(@Pattern (regexp = EMAIL_REGEX)@RequestParam("email") String email,
                                      @SessionAttribute("accountUser") AccountUser accountUser){
        if(userService.saveEmail(email,accountUser.getId())){
            accountUser.setEmail(email);//session中的也要进行相应修改
            return RestBean.success();
        }else{
            return RestBean.failure(400,"邮件地址已被其他用户使用，无法修改");
        }
    }

    @PostMapping("change-password")
    public RestBean<String> changePassword(@Length(min = 6, max =16) @RequestParam("old") String old_paw,
                                           @Length(min = 6, max =16) @RequestParam("new") String new_paw,
                                           @SessionAttribute("accountUser") AccountUser accountUser){
        if(userService.changePassword(old_paw, new_paw, accountUser.getId())){
            return RestBean.success();
        }else{
            return RestBean.failure(400, "原密码错误");
        }
    }
}
