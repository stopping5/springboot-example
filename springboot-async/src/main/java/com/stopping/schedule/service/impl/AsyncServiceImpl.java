package com.stopping.schedule.service.impl;

import com.stopping.schedule.service.AsyncService;
import com.stopping.schedule.task.AsyncTask;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @Description AsyncServiceImpl
 * @Author stopping
 * @date: 2021/6/8 23:01
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    @Resource
    AsyncTask asyncTask;
    @Override
    public void sendMsg(String msg) throws ExecutionException, InterruptedException {
        System.out.println("执行业务逻辑...");
        System.out.println("主线程打印："+asyncTask.doTaskTwo().get());
        asyncTask.doTaskOne();
        System.out.println("主业务完毕...");
    }
}
