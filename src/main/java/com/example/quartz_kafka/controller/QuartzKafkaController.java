package com.example.quartz_kafka.controller;

import com.example.quartz_kafka.request.QuartzKafkaRequest;
import com.example.quartz_kafka.response.QuartzKafkaResponse;
import com.example.quartz_kafka.service.QuartzKafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/kafka")
public class QuartzKafkaController {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final QuartzKafkaService service;

    public QuartzKafkaController(QuartzKafkaService service) {
        this.service = service;
    }

    @PostMapping("/topic")
    public String createTopic(@RequestParam String topicName) {
        service.createTopic(topicName);
        return "OK";
    }

    @PostMapping("/record")
    public QuartzKafkaResponse sendRecord(@RequestBody QuartzKafkaRequest request) {
        System.out.format("topicName: %s, record: id: %d, value: %s%n", request.getTopicName(), request.getRecord().getId(), request.getRecord().getValue());
        service.sendRecord(request.getTopicName(), Integer.toString(request.getRecord().getId()), request.getRecord().getValue());
        return new QuartzKafkaResponse(ID_GENERATOR.incrementAndGet(), LocalDateTime.now().toString());
    }

}
