package com.autocrypt.collector.service;

import com.autocrypt.collector.common.PropertyFactory;
import com.autocrypt.collector.controller.dto.ProduceData;
import com.autocrypt.collector.common.consumer.Consumer;
import com.autocrypt.collector.repository.KafkaTopicRepository;
import com.autocrypt.collector.repository.entity.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTopicRepository kafkaTopicRepository;

    @Value("${collector-uri.can-collector}")
    private String canLogAddress;

    @Value("${collector-uri.host-collector}")
    private String hostLogAddress;

    private ArrayList<Consumer> consumers;

    // Send Can or Host Data to Kafka Server for TEST
    public void produceData(ProduceData produceData) {
        Properties configs = PropertyFactory.produce(produceData.getIp_addr(), produceData.getPort());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(configs);
        kafkaProducer.send(new ProducerRecord<String, String>(produceData.getTopic(), produceData.getData()));
    }

    // Starting Kafka Consumer Thread
    public void startConsumer() {
        List<KafkaTopic> TOPIC_NAMES = kafkaTopicRepository.findAll();
        consumers = new ArrayList<>();

        for (KafkaTopic kafkaTopic: TOPIC_NAMES) {
            Consumer consumer = new Consumer(kafkaTopic, canLogAddress, hostLogAddress);
            consumers.add(consumer);
        }

        for (Thread thread: consumers) {
            thread.start();
        }
    }
}
