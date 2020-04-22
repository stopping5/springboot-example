package com.springboot.stopping.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: stopping
 * @Date: 2020/04/22 12:22
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@Service
public class RedisService {
    /*
     * @Description 注入操作redis对象
     * @Param null
     * @Return
     * @Date 2020/4/22 12:39
     * @Author stopping
     */
    @Resource
    private  StringRedisTemplate stringRedisTemplate;

    /*
     * @Description redis添加数据
     * @Param key
     * @param val
     * @Return void
     * @Date 2020/4/22 12:40
     * @Author stopping
     */
    public void setString(String key,String val){
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,val);
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
