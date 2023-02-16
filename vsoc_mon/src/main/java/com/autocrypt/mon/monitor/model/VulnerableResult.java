package com.autocrypt.mon.monitor.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class VulnerableResult {

    private String location;
    private double otl;
    private Long danger;
    private Long warning;

}
