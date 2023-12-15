package com.example.kafkaspring;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaSendController {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @GetMapping("send")
    public void sendMessages(@RequestParam Integer count) {
        for (int i = 0; i < count; i++) {
            kafkaTemplate.send("ilk-topic",
                               "message-" + i);
        }
    }

}
