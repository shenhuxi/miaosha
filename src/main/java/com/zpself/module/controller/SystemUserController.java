package com.zpself.module.controller;

import com.zpself.module.entity.User;
import com.zpself.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/7 13:39
 * @Modified By
 */
@RestController
@RequestMapping("/user")
public class SystemUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }
    @PostMapping("/addUser")
    public Object addUser(User user) {
        return userService.addUser(user);
    }
    @PostMapping("/updateUser")
    public Object updateUser(User user) {
        return userService.updateUser(user);
    }
    @GetMapping("/deleteUser")
    public Object deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

}