package com.stopping.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description TaskThreadPoolConfig
 * @Author stopping
 * @date: 2021/6/8 23:38
 */
@Configuration
public class TaskThreadPoolConfig {
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        //多线程名称前缀
        executor.setThreadNamePrefix("taskExecutor-");
        //等待所有任务完成自动销毁
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待事件超时自动销毁
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
