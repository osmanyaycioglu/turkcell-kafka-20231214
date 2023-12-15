package training.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.HashMap;
import java.util.Map;

public class CustomerProducer {
    public static void main(String[] args) throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                       "127.0.0.1:9092,127.0.0.1:9093");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                       IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                       CustomerJsonSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG,
                       "1");
        properties.put(ProducerConfig.RETRIES_CONFIG,
                       "3");
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,
                       "300");

        final Producer<Integer, Customer> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 100; i++) {
            Customer customerLoc = new Customer();
            customerLoc.setFirstName("osman"+i);
            customerLoc.setLastName("yay"+i);
            customerLoc.setPhoneNumber("3278648" + i);
            customerLoc.setHeight(200);
            customerLoc.setWeight(100);

            producer.send(new ProducerRecord<>("turkcell-new-topic",
                                               i,
                                               customerLoc),
                          new Callback() {
                              @Override
                              public void onCompletion(final RecordMetadata metadata,
                                                       final Exception exception) {
                                  if (exception == null) {
                                      System.out.println("Topic : " + metadata.topic() + " partition : " + metadata.partition() +  " offset : " + metadata.offset());
                                  } else {
                                      System.out.println("Error : " + exception.getMessage());
                                  }
                              }
                          });
            System.out.println("****Message Sent : " + "Message-" + i);

        }

        producer.close();
        System.out.println("Sent all messages");

    }

}
