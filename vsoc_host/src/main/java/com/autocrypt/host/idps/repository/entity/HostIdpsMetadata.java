package com.autocrypt.host.idps.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "host_idps_metadata"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostIdpsMetadata implements Persistable <String> {

    @Id @NotNull
    @Column(name = "host_log")
    private String hostLog;

    @NotNull
    @Column(name = "eid")
    private int eid;

    @NotNull
    @Column(name = "ts")
    private Date ts;

    @NotNull
    @Column(name = "dt")
    private String dt;

    @NotNull
    @Column(name = "vn")
    private String vn;

    @NotNull
    @Column(name = "dtn")
    private String dtn;

    @NotNull
    @Column(name = "vehicle_id")
    private String vehicleId;

    @NotNull
    @Column(name = "region")
    private String region;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "policy")
    private String policy;

    @OneToMany(mappedBy = "hostIdpsMetadata", targetEntity = HostIdpsDetectionLog.class)
    private List<HostIdpsDetectionLog> hostIdpsDetectionLogs = new ArrayList<>();

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return hostLog;
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
