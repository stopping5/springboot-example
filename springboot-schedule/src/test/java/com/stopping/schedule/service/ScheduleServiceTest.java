package com.stopping.schedule.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ScheduleServiceTest {

    @Resource
    ScheduleService scheduleService;

    @Test
    public void scheduleTest(){
        scheduleService.sayHello();
    }
}