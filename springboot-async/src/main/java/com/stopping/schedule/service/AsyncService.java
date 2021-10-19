package com.stopping.schedule.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @Description AsyncService
 * @Author stopping
 * @date: 2021/6/8 23:00
 */
@Service
public interface AsyncService {
    public void sendMsg(String msg) throws ExecutionException, InterruptedException;
}
