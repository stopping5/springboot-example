package com.springboot.stopping;

import com.springboot.stopping.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoppingApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(StoppingApplicationTests.class);

    @Resource
    private RedisService redisService;

    @Test
    public void contextLoads() {
        //测试redis String类型
        redisService.setString("name","stopping");

        logger.info("获取redis：key：name,value：{}",redisService.getString("name"));
    }

}
