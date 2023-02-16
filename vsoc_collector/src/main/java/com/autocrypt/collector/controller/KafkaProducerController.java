package com.autocrypt.collector.controller;

import com.autocrypt.collector.controller.dto.ProduceData;
import com.autocrypt.collector.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaService kafkaService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<?> produceData(@RequestBody ProduceData produceData) {
        this.kafkaService.produceData(produceData);
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }
}