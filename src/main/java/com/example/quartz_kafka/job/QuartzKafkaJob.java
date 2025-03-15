package com.example.quartz_kafka.job;

import com.example.quartz_kafka.service.QuartzKafkaService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzKafkaJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzKafkaJob.class);

    private final QuartzKafkaService quartzKafkaService;

    public QuartzKafkaJob(QuartzKafkaService quartzKafkaService) {
        this.quartzKafkaService = quartzKafkaService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("start {}", QuartzKafkaJob.class.getSimpleName());
        quartzKafkaService.sendRecord();
        LOGGER.info("end   {}", QuartzKafkaJob.class.getSimpleName());
    }

}
