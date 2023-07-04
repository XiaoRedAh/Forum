package com.xiaoRed.entity.user;

import lombok.Data;

@Data
public class AccountPrivacy {
    long uid;
    boolean email;
    boolean sex;
    boolean phone;
    boolean qq;
    boolean wx;
    boolean blog;
}
