package com.mumi.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mumi.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
