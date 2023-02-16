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

    @NotNull
    @Column(name = "vehicle_id")
    private String vehicleId;

    @NotNull
    @Column(name = "region")
    private String region;

    @NotNull
    @Column(name = "city")
    private String city;

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
