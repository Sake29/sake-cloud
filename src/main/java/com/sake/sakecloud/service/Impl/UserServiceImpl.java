package com.sake.sakecloud.service.Impl;

import com.sake.sakecloud.entity.User;
import com.sake.sakecloud.mapper.UserMapper;
import com.sake.sakecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserBy(String username) {
        return userMapper.getUserBy(username);
    }

    @Override
    public int registerNewUser(User user) {
        return userMapper.insert(user.getUsername(),user.getPassword());
    }
}
