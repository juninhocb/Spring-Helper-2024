package com.carlosjr.schedulerex;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class ScheduleInitializer {

    @Getter
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        this.scheduler           = factory.getScheduler();
        this.scheduler.start();
        log.info("Schedule started {}", this.scheduler.getSchedulerName());
    }


}
