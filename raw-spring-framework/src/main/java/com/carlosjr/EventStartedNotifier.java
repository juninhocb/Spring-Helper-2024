package com.carlosjr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventStartedNotifier {
    public EventStartedNotifier(){
        System.out.println("Server started at " + LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

}
