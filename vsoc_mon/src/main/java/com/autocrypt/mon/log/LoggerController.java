package com.autocrypt.mon.log;

import com.autocrypt.mon.log.dto.ApiLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LoggerController {

    private final LoggerService loggerService;

    @ResponseBody
    @GetMapping("/api")
    public List<ApiLogDto> getApiLogList() {
        // Request All Api Log Data List
        return loggerService.getApiLogList();
    }
}
