package com.stopping.schedule.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @Description AsyncTask
 * @Author stopping
 * @date: 2021/6/8 22:59
 */
@Component
public class AsyncTask {
    @Async
    public void doTaskOne() {
        String uuid = UUID.randomUUID().toString();
    }

    @Async("taskExecutor")
    public Future<String> doTaskTwo() {
        String uuid = UUID.randomUUID().toString();
        return new AsyncResult<>(uuid);
    }
}
