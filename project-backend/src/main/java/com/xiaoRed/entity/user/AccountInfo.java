package com.xiaoRed.entity.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountInfo {
    long uid;
    @Length(min=2, max=8)
    String username;
    String sex;
    @Length(max=11)
    String phone;
    @Length(max=11)
    String qq;
    @Length(max=30)
    String wx;
    @Length(max=50)
    String blog;
    @Length(max=500)
    String desc;
}
