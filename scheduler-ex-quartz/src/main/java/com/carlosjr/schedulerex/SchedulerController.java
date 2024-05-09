package com.carlosjr.schedulerex;

import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
class SchedulerController {
    private final ScheduleJobsService service;
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void scheduleJob(@RequestBody ScheduleDetailsDto details){
        service.scheduleJob(details);
    }

    @GetMapping
    ResponseEntity<String> getJobs() throws SchedulerException {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(service.getJobs());
    }


}
