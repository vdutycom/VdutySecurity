package com.vduty.vbackstage.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.vduty.vbackstage.entity.UsersBaseEntity;

public interface UserBaseMapper {
	
	/*
     * 通过登录名获取用户
     * 
     */
    @Select("SELECT * FROM users WHERE name = #{name}")
    @Results({    	
          @Result(property = "nickName", column = "nick_name")
          
    })
    public UsersBaseEntity getUserByName(String name);

}
