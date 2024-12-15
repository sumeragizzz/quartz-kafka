package com.example.quartz_kafka.runner;

import com.example.quartz_kafka.job.QuartzKafkaJob;
import com.example.quartz_kafka.service.QuartzKafkaService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuartzKafkaRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        try {
            // スケジューラーの作成
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            // JobDetailの作成
            JobDetail jobDetail = JobBuilder.newJob(QuartzKafkaJob.class)
                    .withIdentity("myJob", "group1")
                    .build();

            // Triggerの作成
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                    .build();

            // JobとTriggerをスケジューラーに追加
            scheduler.scheduleJob(jobDetail, trigger);

            // スケジューラーを開始
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
