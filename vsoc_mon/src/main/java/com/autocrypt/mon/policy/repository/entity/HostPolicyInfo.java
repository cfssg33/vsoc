package com.autocrypt.mon.policy.repository.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "vsoc_host_policy"
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HostPolicyInfo {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "version")
    private String version;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "file_path")
    private String filePath;

    @NotNull
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "updated_user")
    private String updatedBy;

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
