package com.example.quartz_kafka.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class QuartzKafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzKafkaService.class);

    private final KafkaAdmin admin;

    private final KafkaTemplate<String, String> template;

    public QuartzKafkaService(KafkaAdmin admin, KafkaTemplate<String, String> template) {
        this.admin = admin;
        this.template = template;
    }

    public void execute() {
        LOGGER.info("start {}", QuartzKafkaService.class.getSimpleName());

        // 送信するメッセージ
        String topic = "test-topic";
        String key = "my-key";
        String value = LocalDateTime.now().toString();

        try {
            // KafkaTemplateにより非同期でメッセージ送信する
            CompletableFuture<SendResult<String, String>> future = template.send(topic, key, value);
            future.thenAccept(result -> LOGGER.info("Sent message to topic: {}, partition: {}, offset: {}, key: {}, value: {}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), result.getProducerRecord().key(), result.getProducerRecord().value()));
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("end   {}", QuartzKafkaService.class.getSimpleName());
    }

    public void createTopic(String topicName) {
        NewTopic topic = TopicBuilder.name(topicName)
                .partitions(1)
                .build();
        admin.createOrModifyTopics(topic);

        Map<String, TopicDescription> topics = admin.describeTopics(topicName);
        LOGGER.info("topic : {}", topics);
    }

}
