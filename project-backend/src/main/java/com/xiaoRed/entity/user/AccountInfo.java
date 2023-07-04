package com.xiaoRed.entity.user;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountInfo {
    //包含中英文，不含特殊字符的用户名的正则表达式
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
    long uid;
    @Pattern(regexp = USERNAME_REGEX)
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
