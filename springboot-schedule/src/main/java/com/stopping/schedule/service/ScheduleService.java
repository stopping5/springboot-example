package com.stopping.schedule.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description ScheduleService
 * @Author stopping
 * @date: 2021/6/9 23:12
 */
@Service
public class ScheduleService {
    /**
     * second
     * minute
     * hour
     * day of month
     * month
     * day of week
     * */
    @Scheduled(cron = " * 38 23 * * * ")
    public void sayHello(){
        System.out.println(new Date());
    }
}
