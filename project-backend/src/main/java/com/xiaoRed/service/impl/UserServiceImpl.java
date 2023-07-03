package com.xiaoRed.service.impl;

import com.xiaoRed.entity.auth.Account;
import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.mapper.UserMapper;
import com.xiaoRed.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public boolean saveUserInfo(AccountInfo accountInfo) {
        //根据提交的表单的用户名查找数据库中的用户
        Account account = userMapper.findAccountByNameOrEmail(accountInfo.getUsername());
        if(account==null) {//如果没有，那么可以正常改名
            userMapper.updateUsername(accountInfo.getUsername(), accountInfo.getUid());
        }else if(account.getId()!=accountInfo.getUid()){//如果有这个用户名，且并非当前账号，则不能改名，修改失败
            return false;
        }
        //正常修改信息：1、更改的用户名在数据库中不存在 2、虽存在，但是自己账户的，那也可以改
        userMapper.saveInfo(accountInfo);
        return true;
    }

    @Override
    public AccountInfo userInfo(long uid) {
        return userMapper.findInfoById(uid);
    }


}
