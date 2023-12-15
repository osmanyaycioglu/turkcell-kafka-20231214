package training.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomerJsonSerializer implements Serializer<Customer> {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public byte[] serialize(final String topic,
                            final Customer data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException eParam) {
            throw new SerializationException(eParam.getMessage());
        }
    }

}
