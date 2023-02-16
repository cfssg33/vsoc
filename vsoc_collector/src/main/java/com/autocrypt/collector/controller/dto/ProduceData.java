package com.autocrypt.collector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProduceData {

    private String ip_addr;
    private int port;
    private String data;
    private String topic;

}
