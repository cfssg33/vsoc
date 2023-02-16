package com.autocrypt.mon.apilog.entity;

import com.autocrypt.mon.log.dto.ApiLogDto;
import com.autocrypt.mon.log.model.ApiLogMapper;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "vsoc_api_log"
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApiLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Size(max = 500)
    @NotNull
    @Column(name = "url")
    private String url;

    @Size(max = 16)
    @NotNull
    @Column(name = "method")
    private String method;

    @Size(max = 10)
    @NotNull
    @Column(name = "level")
    private String level;

    @Size(max = 128)
    @NotNull
    @Column(name = "source_ip")
    private String sourceIp;

    @Size(max = 5000)
    @NotNull
    @Column(name = "request")
    private String request;

    @Size(max = 10000)
    @NotNull
    @Column(name = "response")
    private String response;

    @Column(name = "processing_time")
    private Duration processingTime;

    @Transient
    public static ApiLogDto apiLogEntityToApiLogDTO(ApiLog apiLog) {
        return ApiLogMapper.INSTANCE.apiLogEntityToApiLogDTO(apiLog);
    }

    @Transient
    public String getProcessingTimeString() {
        if (this.processingTime == null) {
            return "None";
        } else {
            return Long.toString(this.processingTime.getSeconds());
        }
    }
}
