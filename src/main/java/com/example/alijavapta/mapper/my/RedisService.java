package com.example.alijavapta.mapper.my;

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
        ValueOperations<String, String> ops=stringRedisTemplate.opsForValue();
        ops.set(user.getUserName(), user.getPassword());
        return true;
    }

    public String getUserByStringRedisTemplate(String name){
        ValueOperations<String, String> ops=stringRedisTemplate.opsForValue();
        return ops.get(name);
    }

    public boolean setString(String key,String value){
        ValueOperations<String, String> ops=stringRedisTemplate.opsForValue();
        ops.set(key,value);
        return true;
    }

    public String getString(String key){
        ValueOperations<String, String> ops=stringRedisTemplate.opsForValue();
        return ops.get(key);
    }
}