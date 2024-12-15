package com.example.quartz_kafka.job;

import com.example.quartz_kafka.QuartzKafkaApplication;
import com.example.quartz_kafka.service.QuartzKafkaService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QuartzKafkaJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzKafkaJob.class);

    private final QuartzKafkaService quartzKafkaService;

    public QuartzKafkaJob() {
        ApplicationContext context = new AnnotationConfigApplicationContext(QuartzKafkaApplication.class);
        this.quartzKafkaService = context.getBean(QuartzKafkaService.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("start {}", QuartzKafkaJob.class.getSimpleName());
        quartzKafkaService.execute();
        LOGGER.info("end   {}", QuartzKafkaJob.class.getSimpleName());
    }

}
