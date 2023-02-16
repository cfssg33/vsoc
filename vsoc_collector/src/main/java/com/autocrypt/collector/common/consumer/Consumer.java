package com.autocrypt.collector.common.consumer;

import com.autocrypt.collector.common.Divisions;
import com.autocrypt.collector.common.PropertyFactory;
import com.autocrypt.collector.common.KafkaTopicType;
import com.autocrypt.collector.common.consumer.dto.CanIdsDTO;
import com.autocrypt.collector.common.consumer.dto.HostIdpsDTO;
import com.autocrypt.collector.common.consumer.dto.KafkaDataDto;
import com.autocrypt.collector.common.consumer.dto.SendLogDTO;
import com.autocrypt.collector.repository.entity.KafkaTopic;
import com.autocrypt.collector.repository.entity.VehicleInfo;
import com.autocrypt.collector.service.VehicleInfoService;
import com.autocrypt.collector.utils.BeanUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

// Kafka Consumer For Receiving Detection Log from Kafka Server, One Consumer per kafka topic
@Slf4j
public class Consumer extends Thread {

    private final VehicleInfoService vehicleInfoService;

    private final KafkaTopic topic;

    private final String canLogAddress;
    private final String hostLogAddress;

    public Consumer(KafkaTopic topic, String canLogAddress, String hostLogAddress) {
        vehicleInfoService = (VehicleInfoService) BeanUtils.getBean(VehicleInfoService.class);
        this.topic = topic;
        this.canLogAddress = canLogAddress;
        this.hostLogAddress = hostLogAddress;
    }

    private void sendCanLog(String data) {
        // Send Can Log Data to vsoc_can by controller
        try {
            ResponseEntity<String> res = new RestTemplate().postForEntity(canLogAddress + "/can/ids/log", data, String.class);
        } catch (Exception e) {
            log.error("####################################################################################");
            log.error("Exception Occurred With sendCanLog :" + e);
            log.error("####################################################################################");
        }
    }

    private void sendCanLog(KafkaDataDto data) {
        // Send Can Log Data to vsoc_can by kafka Server
        try {
            String[] splicedLocation = Divisions.splitLocation(data.getLocation());
            CanIdsDTO canIdsDTO = CanIdsDTO.builder()
                    .vehicleInfoPk(data.getTuid())
                    .region(splicedLocation[0])
                    .city(splicedLocation[1])
                    .binary(data.getData())
                    .build();
            VehicleInfo vehicleInfo = vehicleInfoService.saveAndGetVehicleInfoByTUID(data.getTuid(), splicedLocation[0], splicedLocation[1]);
            ResponseEntity<SendLogDTO> response = new RestTemplate().exchange(
                    canLogAddress + "/can/ids/log",
                    HttpMethod.POST,
                    new HttpEntity<>(canIdsDTO, new HttpHeaders()),
                    SendLogDTO.class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                vehicleInfoService.updateVehicleSeriousness(vehicleInfo, response.getBody().getSeriousness());
            } else {
                throw new Exception("Exception Occurred With VSOC_CAN : Response : " + response);
            }
        } catch (Exception e) {
            log.error("####################################################################################");
            log.error("Exception Occurred With sendCanLog :" + e);
            log.error("####################################################################################");
        }
    }

    private void sendHostLog(String data) {
        // Send Host Log Data to vsoc_host by controller
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            HostIdpsDTO hostIdpsLogDTO = mapper.readValue(data, HostIdpsDTO.class);
            new RestTemplate().put(hostLogAddress + "/host/idps/log", hostIdpsLogDTO);
        } catch (Exception e) {
            log.error("####################################################################################");
            log.error("Exception Occurred With sendHostLog: " + e);
            log.error("####################################################################################");
        }
    }

    private void sendHostLog(KafkaDataDto data) {
        // Send Host Log Data to vsoc_host by kafka Server
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String[] splicedLocation = Divisions.splitLocation(data.getLocation());
            HostIdpsDTO hostIdpsLogDTO = mapper.readValue(data.getData(), HostIdpsDTO.class);
            hostIdpsLogDTO.setVehicleId(data.getTuid());
            hostIdpsLogDTO.setRegion(splicedLocation[0]);
            hostIdpsLogDTO.setCity(splicedLocation[1]);
            VehicleInfo vehicleInfo = vehicleInfoService.saveAndGetVehicleInfoByTUID(data.getTuid(), splicedLocation[0], splicedLocation[1]);
            ResponseEntity<SendLogDTO> response = new RestTemplate().exchange(
                    hostLogAddress + "/host/idps/log",
                    HttpMethod.PUT,
                    new HttpEntity<HostIdpsDTO>(hostIdpsLogDTO, new HttpHeaders()),
                    SendLogDTO.class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                vehicleInfoService.updateVehicleSeriousness(vehicleInfo, response.getBody().getSeriousness());
            } else {
                throw new Exception("Exception Occurred With VSOC_HOST : Response : " + response);
            }
        } catch (Exception e) {
            log.error("####################################################################################");
            log.error("Exception Occurred With sendHostLog: " + e);
            log.error("####################################################################################");
        }
    }

    @Override
    public void run() {
        Properties configs = PropertyFactory.produce(topic.getIpAddress(), topic.getPort());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(configs);
        consumer.subscribe(Arrays.asList(topic.getTopic()));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                // All Consumers receive All Data and Act according to its own topic
                if (KafkaTopicType.CAN_LOG_TEST.equals(topic.getType())) {
                    sendCanLog(record.value());
                } else if (KafkaTopicType.HOST_LOG_TEST.equals(topic.getType())) {
                    sendHostLog(record.value());
                } else if (KafkaTopicType.CAN_LOG.equals(topic.getType())) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        KafkaDataDto kafkaDataDto = objectMapper.readValue(record.value(), KafkaDataDto.class);
                        sendCanLog(kafkaDataDto);
                    } catch (Exception e) {
                        log.error("##########################################");
                        log.error("exception canLogMapping: " + e);
                        log.error("##########################################");
                    }
                } else if (KafkaTopicType.HOST_LOG.equals(topic.getType())) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        KafkaDataDto kafkaDataDto = objectMapper.readValue(record.value(), KafkaDataDto.class);
                        sendHostLog(kafkaDataDto);
                    } catch (Exception e) {
                        log.error("##########################################");
                        log.error("exception hostLogMapping: " + e);
                        log.error("##########################################");
                    }
                }
            }
            try {
                // Receiving Data from Kafka Server every 100 milliseconds
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
