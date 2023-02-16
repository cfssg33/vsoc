package com.autocrypt.mon.log;

import com.autocrypt.mon.apilog.ApiLogRepository;
import com.autocrypt.mon.apilog.entity.ApiLog;

import com.autocrypt.mon.log.dto.ApiLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoggerService {

    private final ApiLogRepository apiLogRepository;

    private static final int MAX_REQUEST_LENGTH = 5000;
    private static final int MAX_RESPONSE_LENGTH = 10000;

    @Transactional
    public void InsertApiLog(String url,
                             int status,
                             String remoteAddr,
                             String httpMethod,
                             String request,
                             String response,
                             Duration processingTime) {
        // Store new Api Log by Api Logging Interceptor
        String logLevel = HttpStatus.valueOf(status).is2xxSuccessful() ? "SUCCESS" : "ERROR";

        ApiLog apiLog = ApiLog.builder()
                .url(url)
                .level(logLevel)
                .method(httpMethod)
                .sourceIp(remoteAddr)
                .request(request.length() > MAX_REQUEST_LENGTH ? "Too Long Data" : request)
                .response(response.length() > MAX_RESPONSE_LENGTH ? "Too Long Data" : response)
                .processingTime(processingTime)
                .build();

        apiLogRepository.save(apiLog);
    }

    public List<ApiLogDto> getApiLogList() {
        List<ApiLogDto> apiLogDtos = new ArrayList<>();
        List<ApiLog> apiLogList = apiLogRepository.findAllByOrderByCreatedTimeDesc();
        for (ApiLog apiLog: apiLogList) {
            apiLogDtos.add(ApiLog.apiLogEntityToApiLogDTO(apiLog));
        }
        return apiLogDtos;
    }

}
