package com.example.alijavapta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributeLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /** * 获取分布式锁 * * @param lockName 锁名-key * @param lockValue 锁值-value * @param expireTime 锁过期时间：防止锁未释放造成死锁 * @param timeUnit 时间单位 * @return true/false -- 成功/失败 */
    public boolean tryLock(String lockName, String lockValue, long expireTime, TimeUnit timeUnit) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(lockName, lockValue, expireTime, timeUnit);
        return res != null && res;
    }

    /** * 获取分布式锁--使用lua脚本 * * @param lockName 锁名-key * @param lockValue 锁值-value * @param expireTime 锁过期时间，单位秒：防止锁未释放造成死锁 * @return true/false -- 成功/失败 */
    public boolean tryLock(String lockName, String lockValue, long expireTime) {
        String setNxExpScript = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 "
                + " then redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        List<String> keys = Arrays.asList(lockName);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText(setNxExpScript);
        Long res = redisTemplate.execute(script, keys, lockValue, expireTime);
        return res != null && res == 1;
    }

    /** * 释放锁 * * @param lockName 锁名-key * @param lockValue 锁值-value */
    public boolean releaseLock(String lockName, String lockValue) {
        String releaseScript = "if redis.call('get',KEYS[1]) == ARGV[1] "
                + " then return redis.call('del',KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText(releaseScript);
        Long res = redisTemplate.execute(script, Arrays.asList(lockName), lockValue);
        return res != null && res >= 1;
    }

}