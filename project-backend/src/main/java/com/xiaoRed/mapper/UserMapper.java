package com.xiaoRed.mapper;

import com.xiaoRed.entity.auth.Account;
import com.xiaoRed.entity.user.AccountInfo;
import com.xiaoRed.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);
    @Select("select * from db_account where username = #{text} or email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);
    @Insert("insert into db_account(username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(String username, String password, String email);
    @Update("update db_account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);

    @Insert(" insert into db_account_info(uid, sex, phone, qq, wx, blog, `desc`) " +
            "values(#{uid}, #{sex}, #{phone}, #{qq}, #{wx}, #{blog}, #{desc}) " +
            "on duplicate key update uid=#{uid}, sex=#{sex}, phone=#{phone}, " +
            "qq=#{qq},wx=#{wx},blog=#{blog},`desc`=#{desc}")//出现主键重复的时候，只用做更新即可，而不是插入
    void saveInfo(AccountInfo accountInfo);

    @Update("update db_account set username = #{name} where id = #{uid}")
    void updateUsername(String name,long uid);

    @Select("select * from db_account left join db_account_info on id = uid where id =#{uid}")
    AccountInfo findInfoById(long uid);
    @Update("update db_account set email = #{email} where id = #{uid}")
    void updateEmail(String email, long uid);
    @Update("update db_account set password = #{encode} where id = #{uid}")
    void updatePassword(String encode, long uid);
    @Select("select * from db_account where id = #{uid}")
    Account findAccountById(long uid);
}
