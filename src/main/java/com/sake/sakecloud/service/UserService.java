package com.sake.sakecloud.service;

import com.sake.sakecloud.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    User getUserBy(String username);

    @Transactional
    void registerNewUser(User user);
}
