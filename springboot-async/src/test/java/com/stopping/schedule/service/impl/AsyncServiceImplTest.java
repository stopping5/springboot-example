package com.stopping.schedule.service.impl;

import com.stopping.schedule.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class AsyncServiceImplTest {
    @Resource
    private AsyncService asyncService;

    @Test
    void sendMsg() throws ExecutionException, InterruptedException {
        asyncService.sendMsg("hello");
    }
}