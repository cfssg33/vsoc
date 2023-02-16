package com.autocrypt.collector.common;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;
import java.util.UUID;

public abstract class PropertyFactory {
    // Build default Kafka Properties
    public static Properties produce(String ipAddress, int port) {
        Properties configs = new Properties();
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        configs.put("bootstrap.servers", ipAddress + ":" + port);
        configs.put("acks", "all");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        return configs;
    }
}
