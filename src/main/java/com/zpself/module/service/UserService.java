package com.zpself.module.service;

import com.zpself.module.entity.User;
import com.zpself.module.repository.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    SystemUserMapper userMapper;

    public User getUserById(long userId) {
        return  userMapper.selectById(userId);
    }
    public Object addUser(User user) {
        userMapper.insert(user);
        return user;
    }
    public Object updateUser(User user) {
        userMapper.updateById(user);
        return user;
    }
    public Object deleteUser(long userId) {
        return userMapper.deleteById(userId);
    }
}
