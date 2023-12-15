package training.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyKafkaStream {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                       "127.0.0.1:9092,127.0.0.1:9093");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                       "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                       Serdes.Integer()
                             .getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                       Serdes.String()
                             .getClass());
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,
                       "my_kstream_app");
        properties.put(StreamsConfig.CLIENT_ID_CONFIG,
                       "kstream-client-1");
        properties.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG,
                       5);
        ObjectMapper objectMapperLoc = new ObjectMapper();

        StreamsBuilder           streamsBuilderLoc = new StreamsBuilder();
        KStream<Integer, String> streamLoc         = streamsBuilderLoc.stream("ilk-topic");
        streamLoc.mapValues(v -> {
                     Customer customerLoc = new Customer();
                     customerLoc.setFirstName(v);
                     customerLoc.setLastName("test");
                     customerLoc.setWeight(100);
                     customerLoc.setHeight(200);
                     return customerLoc;
                 })
                 .mapValues(c -> {
                     try {
                         return objectMapperLoc.writeValueAsString(c);
                     } catch (JsonProcessingException eParam) {
                         eParam.printStackTrace();
                     }
                     return null;
                 })
                 .to("customer-dest",
                     Produced.with(Serdes.Integer(),
                                   Serdes.String()));
        streamLoc.mapValues(v -> {
                     Customer customerLoc = new Customer();
                     customerLoc.setFirstName(v);
                     customerLoc.setLastName("test");
                     customerLoc.setWeight(100);
                     customerLoc.setHeight(200);
                     return customerLoc;
                 })
                 .mapValues(c -> {
                     try {
                         return objectMapperLoc.writeValueAsString(c);
                     } catch (JsonProcessingException eParam) {
                         eParam.printStackTrace();
                     }
                     return null;
                 })
                 .to("turkcell-new-topic",
                     Produced.with(Serdes.Integer(),
                                   Serdes.String()));


        streamLoc.foreach((k, v) -> System.out.println("Key : " + k + " Value : " + v));

        Topology     topologyLoc     = streamsBuilderLoc.build();
        KafkaStreams kafkaStreamsLoc = new KafkaStreams(topologyLoc,
                                                        properties);
        Runtime.getRuntime()
               .addShutdownHook(new Thread(kafkaStreamsLoc::close));

        kafkaStreamsLoc.start();

    }

}
