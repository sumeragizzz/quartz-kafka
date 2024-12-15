package com.example.quartz_kafka.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class QuartzKafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzKafkaService.class);

    public void execute() {
        LOGGER.info("start {}", QuartzKafkaService.class.getSimpleName());

        // Kafkaプロデューサーの設定
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Kafkaプロデューサーのインスタンス作成
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 送信するメッセージ
        String topic = "test-topic";
        String key = "my-key";
        String value = "Hello, Kafka!";

        // プロデューサーレコードの作成
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

        try {
            // メッセージを送信し、結果を取得
            RecordMetadata metadata = producer.send(record).get();
            System.out.printf("Sent message to topic:%s partition:%d offset:%d%n", metadata.topic(), metadata.partition(), metadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // プロデューサーをクローズ
            producer.close();
        }

        LOGGER.info("end   {}", QuartzKafkaService.class.getSimpleName());
    }

}
