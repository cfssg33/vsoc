package com.autocrypt.mon.hostidps.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(
        name = "host_idps_status_log"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostIdpsStatusLog implements Persistable<String> {

    @Id @NotNull
    @Column(name = "uid")
    private String uid;

    @NotNull
    @Column(name = "event_time")
    private Date eventTime;

    @NotNull
    @Column(name = "ids_policy_ver")
    private String idsPolicyVer;

    @NotNull
    @Column(name = "fw_policy_ver")
    private String fwPolicyVer;

    @NotNull
    @Column(name = "traffic_rx")
    private int trafficRx;

    @NotNull
    @Column(name = "traffic_tx")
    private int trafficTx;

    @NotNull
    @Column(name = "log_uuid")
    private String logUUID;

    @Column(name = "checked_log")
    private String checkedLog;

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
}
