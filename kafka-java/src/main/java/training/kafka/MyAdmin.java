package training.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyAdmin {
    public static void main(String[] args) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                       "127.0.0.1:9092,127.0.0.1:9093");
        try (Admin adminLoc = Admin.create(properties)) {
            boolean                               createTopic         = true;
            ListTopicsResult                      listTopicsResultLoc = adminLoc.listTopics();
            KafkaFuture<Collection<TopicListing>> listingsLoc         = listTopicsResultLoc.listings();
            Collection<TopicListing>              topicListingsLoc    = listingsLoc.get();
            for (TopicListing topicListingLoc : topicListingsLoc) {
                System.out.println(topicListingLoc.toString());
                String nameLoc = topicListingLoc.name();
                if (nameLoc.equals("turkcell-new-topic")) {
                    createTopic = false;
                }
                DescribeTopicsResult                       describeTopicsResultLoc      = adminLoc.describeTopics(Collections.singleton(nameLoc));
                KafkaFuture<Map<String, TopicDescription>> mapKafkaFutureLoc            = describeTopicsResultLoc.allTopicNames();
                Map<String, TopicDescription>              stringTopicDescriptionMapLoc = mapKafkaFutureLoc.get();
                TopicDescription                           topicDescriptionLoc          = stringTopicDescriptionMapLoc.get(nameLoc);
                System.out.println(topicDescriptionLoc.toString());

            }
            if (createTopic) {
                NewTopic newTopicLoc = new NewTopic("turkcell-new-topic",
                                                    10,
                                                    (short) 3);
                CreateTopicsResult createTopicsResultLoc = adminLoc.createTopics(Collections.singleton(newTopicLoc));
                KafkaFuture<Void> voidKafkaFutureLoc = createTopicsResultLoc.values()
                                                                            .get("turkcell-new-topic");
                voidKafkaFutureLoc.get();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
