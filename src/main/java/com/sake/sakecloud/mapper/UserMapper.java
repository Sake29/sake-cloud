package com.sake.sakecloud.mapper;

import com.sake.sakecloud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User getUserBy(@Param("username") String username);

    int insert(@Param("username")String userName, @Param("password")String password);
}