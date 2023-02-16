package com.autocrypt.collector.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "kafka_topic")
@Getter
@Setter
@NoArgsConstructor
public class KafkaTopic {
    @Id
    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "topic")
    private String topic;

    @NotNull
    @Column(name = "ip_addr")
    private String ipAddress;

    @NotNull
    @Column(name = "port")
    private int port;
}
