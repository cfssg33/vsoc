package com.autocrypt.can.ids.repository.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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
    private int logType;

    @NotNull
    @Column(name = "data_length")
    private int dataLength;

    @NotNull
    @Column(name = "sequence_number")
    private int sequenceNumber;

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
