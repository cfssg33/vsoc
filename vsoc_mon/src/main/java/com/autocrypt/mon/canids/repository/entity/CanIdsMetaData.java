package com.autocrypt.mon.canids.repository.entity;

import com.autocrypt.mon.vehicleInfo.repository.entity.VehicleInfo;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(
        name = "can_ids_meta_data"
)
@Getter
@Setter
@NoArgsConstructor
public class CanIdsMetaData implements Persistable<String> {
    @Id
    @NotNull
    @Column(name = "id")
    String id; // 32byte

    @NotNull
    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(targetEntity = VehicleInfo.class)
    @JoinColumn(name = "vehicle_id")
    private VehicleInfo vehicleInfo;

    @NotNull
    @Column(name = "region")
    private String region;

    @NotNull
    @Column(name = "city")
    private String city;

    @OneToOne(targetEntity = CanIdsStatusInfoLog.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private CanIdsStatusInfoLog canIdsStatusInfoLog = new CanIdsStatusInfoLog();

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
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
