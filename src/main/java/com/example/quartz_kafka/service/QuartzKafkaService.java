package com.example.quartz_kafka.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class QuartzKafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzKafkaService.class);

    private final KafkaTemplate<String, String> template;

    public QuartzKafkaService(KafkaTemplate<String, String> template) {
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

}
