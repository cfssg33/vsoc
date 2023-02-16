package com.autocrypt.mon.util.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SessionInfoDto {
    private final String id;
    private final String remoteAddr;
    private final String loginId;
    private final String role;
}
