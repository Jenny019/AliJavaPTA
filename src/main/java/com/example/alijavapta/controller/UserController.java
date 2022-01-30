package com.example.alijavapta.controller;

import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping(value = "/findAllUser")
    public Iterable<User> getAllUser() {
        return userMapper.ListUsers();
    }

    @RequestMapping(value = "/findCoupons")
    public Iterable<CouponRecord> findCoupons(Condition condition) {
        return userMapper.ListCoupons(condition);
    }

    @RequestMapping(value = "/findAddresses")
    public Iterable<Address> findAddress(Condition condition) {
        return userMapper.ListAddresses(condition);
    }

    @RequestMapping(value = "/findRoles")
    public Iterable<Role> findRoles(Condition condition) {
        return userMapper.ListRoles(condition);
    }

    @RequestMapping("/saveUser")
    public Object getAllUser(User user) {
//        userRepository.save(user);
        return "{\"msg\":\"添加数据成功\"}";
    }
}
