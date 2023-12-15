package com.example.kafkaspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class CustomerJsonDeserilizer implements Deserializer<Customer> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Customer deserialize(final String topic,
                                final byte[] data) {
        try {
            return objectMapper.readValue(data, Customer.class);
        } catch (IOException eParam) {
            throw new SerializationException(eParam.getMessage());
        }
    }

}
