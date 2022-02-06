package com.example.alijavapta.mapper;

import com.example.alijavapta.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean setUserByStringRedisTemplate(User user){
        ValueOperations ops=stringRedisTemplate.opsForValue();
        ops.set(user.getUserName(), user.getPassword());
        return true;
    }

    public String getUserByStringRedisTemplate(String name){
        ValueOperations ops=stringRedisTemplate.opsForValue();
        return ops.get(name).toString();
    }

    public boolean setString(String key,String value){
        ValueOperations ops=stringRedisTemplate.opsForValue();
        ops.set(key,value);
        return true;
    }

    public String getString(String key){
        ValueOperations ops=stringRedisTemplate.opsForValue();
        return (String)ops.get(key);
    }
}