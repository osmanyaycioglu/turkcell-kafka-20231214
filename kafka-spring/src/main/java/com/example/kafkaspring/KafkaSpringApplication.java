package com.example.kafkaspring;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaSpringApplication {


    @Bean
    public NewTopic newTopic(){
        NewTopic newTopicLoc = new NewTopic("turkcell-new-topic",
                                            10,
                                            (short) 3);
        return newTopicLoc;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringApplication.class,
                              args);
    }

}
