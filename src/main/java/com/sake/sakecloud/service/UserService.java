package com.sake.sakecloud.service;

import com.sake.sakecloud.entity.User;

public interface UserService {

    User getUserBy(String username);

    int registerNewUser(User user);
}
