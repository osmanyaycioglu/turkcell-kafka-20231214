package com.example.kafkaspring;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class MyKafkaListener {
    private Executor executor = Executors.newFixedThreadPool(20);


    @KafkaListener(topics = "ilk-topic", id = "spring-consumer-1", groupId = "sp-consumer-1", concurrency = "1")
    public void read1(final String stringParam) {
        // 100ms

        executor.execute(() -> System.out.println("1-Received : "
                                                  + stringParam
                                                  + " Thread : "
                                                  + Thread.currentThread()
                                                          .getName()
                                                  + "/"
                                                  + Thread.currentThread()
                                                          .getId()));
    }


//    @KafkaListener(topics = "ilk-topic", id = "spring-consumer-2", groupId = "sp-consumer-1")
//    public void read2(String stringParam) {
//        System.out.println("2-Received : " + stringParam + " Thread : " + Thread.currentThread().getName() + "/" + Thread.currentThread().getId());
//    }

}
