package com.example.quartz_kafka.config;

import com.example.quartz_kafka.job.QuartzKafkaJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class JobConfig {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(QuartzKafkaJob.class);
        factory.setDurability(true);
        return factory;
    }

    @Bean
    public SimpleTriggerFactoryBean jobTrigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setRepeatInterval(10 * 1000L);
        return factory;
    }

}
