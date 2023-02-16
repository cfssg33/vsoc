package com.autocrypt.mon.log.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogDto {
    private Long id;
    private String createdTime;
    private String url;
    private String method;
    private String level;
    private String sourceIp;
    private String request;
    private String response;
    private String processingTime;
}
