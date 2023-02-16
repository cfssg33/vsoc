package com.autocrypt.mon.hostidps.repository.entity;

import com.autocrypt.mon.hostidps.HostIdpsLogMapping;
import com.autocrypt.mon.hostidps.dto.HostIdpsDto;
import com.autocrypt.mon.hostidps.model.HostIdpsLogMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(
        name = "host_idps_detection_log"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostIdpsDetectionLog implements Persistable<String> {

    @Id @NotNull
    @Column(name = "uid")
    private String uid;

    @ManyToOne(targetEntity = HostIdpsMetadata.class)
    @JoinColumn(name = "log_uuid")
    private HostIdpsMetadata hostIdpsMetadata;

    @NotNull
    @Column(name = "event_time")
    private Date eventTime;

    @NotNull
    @Column(name = "protocol")
    private String protocol;

    @NotNull
    @Column(name = "rule_id")
    private int ruleId;

    @NotNull
    @Column(name = "src_ip")
    private String srcIp;

    @NotNull
    @Column(name = "src_port")
    private int srcPort;

    @NotNull
    @Column(name = "dst_ip")
    private String dstIp;

    @NotNull
    @Column(name = "dst_port")
    private int dstPort;

    @NotNull
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "label")
    private String label;

    @Column(name = "checked_log")
    private String checkedLog;

    @Column(name = "severity")
    private String severity;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "rule_type")
    private String ruleType;

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return uid;
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
    public static HostIdpsDto.HostIdpsLog toHostIdpsLog(HostIdpsDetectionLog detectionLog) {

        HostIdpsDto.HostIdpsLog hostIdpsLog = HostIdpsLogMapper.INSTANCE.hostIdpsDetectionLogEntityToDto(detectionLog);
        hostIdpsLog.setVehicleId(detectionLog.getHostIdpsMetadata().getVehicleInfo().getVin());
        hostIdpsLog.setPolicy("Policy_ALSVIN_210");
        hostIdpsLog.setVersion(detectionLog.getHostIdpsMetadata().getVn());

        return hostIdpsLog;
    }
}
