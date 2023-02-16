package com.autocrypt.mon.canids.repository.entity;

import com.autocrypt.mon.canids.CanIdsLogMapping;
import com.autocrypt.mon.canids.dto.CanIdsDto;
import com.autocrypt.mon.canids.model.CanIdsLogMapper;
import com.autocrypt.mon.policy.dto.CanPolicyDto;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne(targetEntity = CanIdsMetaData.class)
    @JoinColumn(name = "match_id")
    private CanIdsMetaData canIdsMetaData;

    @NotNull
    @Column(name = "log_type")
    private byte logType;

    @NotNull
    @Column(name = "data_length")
    private byte dataLength;

    @NotNull
    @Column(name = "by_can_bus_num")
    private byte byCanBusNum;

    @NotNull
    @Column(name = "by_violation_rule_id")
    private byte byViolationRuleId;

    @NotNull
    @Column(name = "by_violation_rule_name")
    private String byViolationRuleName;

    @NotNull
    @Column(name = "signal_start_bit")
    private short signalStartBit;

    @NotNull
    @Column(name = "signal_length")
    private short signalLength;

    @NotNull
    @Column(name = "by_raw_message_length")
    private byte byRawMessageLength;

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

    @Transient
    public static CanIdsDto.CanIdsLog toCanIdsDto(CanIdsDetectionLog detectionLog) {
        String signalId = CanIdsLogMapping.getSignalId(
                detectionLog.getByCanBusNum(),
                detectionLog.getSignalStartBit(),
                detectionLog.getCanId());
        CanPolicyDto.MessageInfo messageInfo =
                CanIdsLogMapping.getMessageInfo(detectionLog.getByCanBusNum(), detectionLog.getCanId());

        CanIdsDto.CanIdsLog canIdsLog = CanIdsLogMapper.INSTANCE.canIdsDetectionLogEntityToDto(detectionLog);
        canIdsLog.setVehicleId(detectionLog.getCanIdsMetaData().getVehicleInfo().getVin());
        canIdsLog.setPolicy("Policy_ALSVIN_210");
        canIdsLog.setVersion(detectionLog.getCanIdsMetaData().getCanIdsStatusInfoLog().getByPolicyVersion());
        canIdsLog.setRuleName(detectionLog.getByViolationRuleName());
        canIdsLog.setTx(messageInfo.getMessageTxEcu());
        canIdsLog.setRx(messageInfo.getMessageRxEcu());
        canIdsLog.setBusId(Byte.toString(detectionLog.getByCanBusNum()));
        canIdsLog.setSignalId(signalId);
        canIdsLog.setReason("reason_hi: " + detectionLog.getDetectionReasonHi() + "\nreason_lo: " + detectionLog.getDetectionReasonLo());
        canIdsLog.setSeverity(detectionLog.getSeverity());

        canIdsLog.setMessage("-");
        return canIdsLog;
    }
}
