package org.example;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Main main = new Main();
        main.doJob();
    }
    private void doJob() throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 60; i++){
            JobService jobService = new JobService(String.valueOf(i));
            service.submit(jobService);
            try {
                Thread.sleep(200);
            }catch (InterruptedException e){
                //ignore
            }
        }
        service.shutdown();
    }
}