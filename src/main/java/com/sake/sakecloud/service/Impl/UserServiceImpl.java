package com.sake.sakecloud.service.Impl;

import com.sake.sakecloud.entity.User;
import com.sake.sakecloud.mapper.UserMapper;
import com.sake.sakecloud.service.UserService;
import com.sake.sakecloud.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileUtil fileUtil;

    @Override
    public User getUserBy(String username) {
        return userMapper.getUserBy(username);
    }

    @Override
    public void registerNewUser(User user) {
        //添加新用户
        userMapper.insert(user);
        //分配存储空间
        fileUtil.initStorageSpace(user);

    }
}
