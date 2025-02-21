package com.example.quartz_kafka.controller;

import com.example.quartz_kafka.service.QuartzKafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class QuartzKafkaController {

    private final QuartzKafkaService service;

    public QuartzKafkaController(QuartzKafkaService service) {
        this.service = service;
    }

    @PostMapping("/topic")
    public String createTopic(@RequestParam String topicName) {
        service.createTopic(topicName);
        return "OK";
    }

}
