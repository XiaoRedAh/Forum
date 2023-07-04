package com.xiaoRed.service;

import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.entity.user.AccountPrivacy;
import com.xiaoRed.entity.user.AccountUser;

public interface UserService {
    boolean saveUserInfo(AccountInfo accountInfo);
    AccountInfo userInfo(long uid);
    boolean saveEmail(String email, long uid);
    boolean changePassword(String old_paw, String new_paw, long uid);
    void saveUserPrivacy(AccountPrivacy accountPrivacy);
    AccountPrivacy userPrivacy(long uid);
}
