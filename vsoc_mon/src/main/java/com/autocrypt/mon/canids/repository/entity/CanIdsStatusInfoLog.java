package com.autocrypt.mon.canids.repository.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;


@Entity
@Table(
        name = "can_ids_status_info_log"
)
@Getter
@Setter
@NoArgsConstructor
public class CanIdsStatusInfoLog implements Persistable<String> {
    @Id
    @NotNull
    @Column(name = "id")
    String id; // 32byte

    @NotNull
    @Column(name = "log_type")
    private byte logType;

    @NotNull
    @Column(name = "data_length")
    private byte dataLength;

    @NotNull
    @Column(name = "sequence_number")
    private short sequenceNumber;

    @NotNull
    @Column(name = "memory_usage")
    private int memoryUsage;

    @NotNull
    @Column(name = "flash_usage")
    private int flashUsage;

    @NotNull
    @Column(name = "policy_version")
    private String byPolicyVersion; // 6byte

    @NotNull
    @Column(name = "firmware_version")
    private String byFirmwareVersion; // 6byte

    @NotNull
    @Column(name = "reserved")
    private String reserved; // 25byte

    @Column(name = "checked_log")
    private String checkedLog;

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
