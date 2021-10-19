package com.example.demo.aspect;

import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Description NoRepeatAop 通过AOP实现接口重复提交
 * @Author stopping
 * @date: 2021/3/3 16:47
 */
@Aspect
@Component
@Slf4j
public class NoRepeatAop {
    @Resource
    private RedisUtil redisUtil;

    @Around("@annotation(com.example.demo.annotation.NoRepeatSubmit)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("拦截请求");
        String token = redisUtil.get("userinfo-13143359896");
        if (!StringUtils.isEmpty(token)) {
            log.info("请勿重复提交数据");
        }
        redisUtil.set("userinfo-13143359896", "0");
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            log.info("del user key");
            redisUtil.delete("userinfo-13143359896");
        }
        return proceedingJoinPoint;
    }
}
