package com.example.alijavapta.controller;

import com.example.alijavapta.domain.User;
import com.example.alijavapta.mapper.my.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/redisTest/setUser")
    public String setUser(){
        User user=new User("quellan","123456");
        redisService.setUserByStringRedisTemplate(user);
        return "添加成功";
    }

    @RequestMapping("/redisTest/getUser")
    public String  getUserByStringRedisTemplate(){
        String name="quellan";
        return redisService.getUserByStringRedisTemplate(name);
    }

    @RequestMapping("/redisTest/setString")
    public String setString(String key ,String value){
        redisService.setString(key,value);
        return "添加成功";
    }

    @RequestMapping("/redisTest/getString")
    public String setString(String key){
        return redisService.getString(key);
    }
}