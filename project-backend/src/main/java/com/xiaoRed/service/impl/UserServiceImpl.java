package com.xiaoRed.service.impl;

import com.xiaoRed.entity.auth.Account;
import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.entity.user.AccountPrivacy;
import com.xiaoRed.mapper.UserMapper;
import com.xiaoRed.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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

    @Override
    public boolean saveEmail(String email, long uid) {
        Account account = userMapper.findAccountByNameOrEmail(email);
        if (account == null) {//新邮箱地址没被用过，可以直接更新
            userMapper.updateEmail(email, uid);
        }else return account.getId()==uid;//如果新邮箱地址和自己原来的一样，则不用改且返回true；否则不能改，且返回false
        return true;
    }

    @Override
    public boolean changePassword(String old_paw, String new_paw, long uid) {
        Account account = userMapper.findAccountById(uid);//通过uid找到当前用户
        //输入的原密码和在数据库中加密存储的密码如果匹配上，才能改密码
        if(encoder.matches(old_paw, account.getPassword())){
            //加密后再存储到数据库
            String encode = encoder.encode(new_paw);
            userMapper.updatePassword(encode, uid);
            return true;
        }else{//原密码输入错误，不能修改密码
            return false;
        }
    }

    @Override
    public void saveUserPrivacy(AccountPrivacy accountPrivacy) {
        userMapper.savePrivacy(accountPrivacy);
    }

    @Override
    public AccountPrivacy userPrivacy(long uid) {
        return userMapper.findPrivacyById(uid);
    }


}
