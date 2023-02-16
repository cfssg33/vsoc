package com.autocrypt.can.ids.repository.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(
        name = "can_ids_detection_log"
)
@Getter
@Setter
@NoArgsConstructor
public class CanIdsDetectionLog implements Persistable<Integer> {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "match_id")
    private String matchId; // 32byte

    @NotNull
    @Column(name = "log_type")
    private int logType;

    @NotNull
    @Column(name = "data_length")
    private int dataLength;

    @NotNull
    @Column(name = "by_can_bus_num")
    private int byCanBusNum;

    @NotNull
    @Column(name = "by_violation_rule_id")
    private int byViolationRuleId;

    @NotNull
    @Column(name = "by_violation_rule_name")
    private String byViolationRuleName;

    @NotNull
    @Column(name = "signal_start_bit")
    private int signalStartBit;

    @NotNull
    @Column(name = "signal_length")
    private int signalLength;

    @NotNull
    @Column(name = "by_raw_message_length")
    private int byRawMessageLength;

    @NotNull
    @Column(name = "can_id")
    private int canId;

    @NotNull
    @Column(name = "detection_time")
    private Date detectionTime;

    @NotNull
    @Column(name = "detection_reason_hi")
    private int detectionReasonHi;

    @NotNull
    @Column(name = "detection_reason_lo")
    private int detectionReasonLo;

    @NotNull
    @Column(name = "duplication_number")
    private int duplicationNumber;

    @NotNull
    @Column(name = "reserved")
    private String reserved; // 14byte

    @NotNull
    @Column(name = "by_raw_message_body")
    private String byRawMessageBody; // 64byte

    @Column(name = "label")
    private String label;

    @Column(name = "checked_log")
    private String checkedLog;

    @Column(name = "severity")
    private String severity;

    @Transient
    private boolean isNew = true;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
