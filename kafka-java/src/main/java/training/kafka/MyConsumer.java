package training.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyConsumer {
    public static void main(String[] args) throws Exception {
        if (args.length != 2 ){
            System.out.println("iki değişken girilmeli");
            System.exit(1);
        }
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                       "127.0.0.1:9092,127.0.0.1:9093");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                       IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                       StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,
                       args[0]);
        properties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,
                       args[1]);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                       "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                       "false");

        final Consumer<Integer, String> consumer = new KafkaConsumer<>(properties);
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));
        consumer.subscribe(Collections.singleton("ilk-topic"));
        while (true) {
            ConsumerRecords<Integer, String> pollLoc = consumer.poll(Duration.ofMillis(1_000));
            if (pollLoc != null && !pollLoc.isEmpty()){
                for (ConsumerRecord<Integer, String> consumerRecordLoc : pollLoc) {
                    System.out.println("Received : " + consumerRecordLoc);
                }
                consumer.commitAsync();
            }
        }
    }

}
