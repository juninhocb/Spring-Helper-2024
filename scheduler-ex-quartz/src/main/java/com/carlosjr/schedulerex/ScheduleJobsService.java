package com.carlosjr.schedulerex;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ScheduleJobsService {
    private final ScheduleInitializer scheduleInitializer;
    void scheduleJob(ScheduleDetailsDto detailsDto){
        JobDetail job = JobBuilder
                .newJob(MyJobExecutor.class)
                .withIdentity(detailsDto.jobName(), "1")
                .build();

        job.getJobDataMap().put("strPar", detailsDto.param1());
        job.getJobDataMap().put("intPar", detailsDto.param2());
        job.getJobDataMap().put("boolPar", detailsDto.param3());

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(detailsDto.jobName(), "1")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 " + detailsDto.time() + " * * ?"))
                .build();

        System.out.println("Configuration done!");


        try {
            verifyAndDeleteSameJobs(detailsDto.jobName());
            scheduleInitializer.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException ex){
             ex.printStackTrace();
        }

    }

    public String getJobs() throws SchedulerException {
        String[] jobs = scheduleInitializer.getScheduler().getJobKeys(GroupMatcher.groupEquals("1"))
                .stream()
                .map(Key::getName)
                .toArray(String[]::new);
        return String.join(" - ", jobs);
    }

    private void verifyAndDeleteSameJobs(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, "1");
        scheduleInitializer.getScheduler().deleteJob(jobKey);
    }

}
