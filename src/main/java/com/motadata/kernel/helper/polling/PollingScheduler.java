package com.motadata.kernel.helper.polling;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class PollingScheduler
{
    SchedulerFactory schedulerFactory;

    Scheduler scheduler;

    public void createScheduler()
    {
        try
        {
            schedulerFactory = new StdSchedulerFactory();

            scheduler = schedulerFactory.getScheduler();

            JobDetail job = JobBuilder.newJob(PollingJob.class)
                    .withIdentity("Polling", "group")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(120).repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startScheduler()
    {
        try
        {
            scheduler.start();

        } catch (SchedulerException e)
        {
            e.printStackTrace();
        }
    }
}
