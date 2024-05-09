package com.carlosjr.schedulerex;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJobExecutor implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Executing this!!! ");
        try {
            String parameter1  = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("strPar");
            Integer parameter2 = (Integer) jobExecutionContext.getJobDetail().getJobDataMap().get("intPar");
            Boolean parameter3 = (Boolean) jobExecutionContext.getJobDetail().getJobDataMap().get("boolPar");

            System.out.println("par1 = " + parameter1 + " par2 = " + parameter2 + " par3 = " + parameter3);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
