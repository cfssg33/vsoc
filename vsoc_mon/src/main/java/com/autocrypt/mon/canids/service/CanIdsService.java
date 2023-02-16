package com.autocrypt.mon.canids.service;

import com.autocrypt.mon.canids.dto.CanIdsDto;
import com.autocrypt.mon.canids.repository.CanIdsDetectionLogRepository;
import com.autocrypt.mon.canids.repository.CanIdsTopFiveRule;
import com.autocrypt.mon.canids.repository.entity.CanIdsDetectionLog;
import com.autocrypt.mon.common.exception.MonIdpsException;
import com.autocrypt.mon.hostidps.dto.HostIdpsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.autocrypt.mon.common.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CanIdsService {

    private final CanIdsDetectionLogRepository canIdsDetectionLogRepository;
    private final ExcelService excelService;

    public CanIdsDto.CanIdsOverall GetCanIdsOverall(String region, String city, String startDate, String endDate) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = transFormat.parse(startDate);
        Date endDateTime = transFormat.parse(endDate);
        Long calculateDate = (endDateTime.getTime() - startDateTime.getTime()) / (60*1000);

        int canIdsTotalDetections = canIdsDetectionLogRepository.totalDetectionCount(startDateTime, endDateTime);
        double canIdsAverage = canIdsDetectionLogRepository.detectionAverage(calculateDate, startDateTime, endDateTime);
        int canIdsDetectionVehicleIdCount = canIdsDetectionLogRepository.countDetectionVehicleId(startDateTime, endDateTime);

        List<CanIdsTopFiveRule> canIdsTopFiveRules = canIdsDetectionLogRepository.countTopFiveRules(startDateTime, endDateTime);

        List<CanIdsDto.CanIdsTopFiveRules> canIdsTopFiveRulesList = new ArrayList<>();
        canIdsTopFiveRules.forEach(i->{
            canIdsTopFiveRulesList.add(new CanIdsDto.CanIdsTopFiveRules(i.getName(), Long.valueOf(i.getCnt())));
        });

        return new CanIdsDto.CanIdsOverall(canIdsTotalDetections,
                canIdsAverage,
                canIdsDetectionVehicleIdCount,
                canIdsTopFiveRulesList);
    }

    public CanIdsDto.CanIdsLogListResponse GetCanIdsLogList(String vehicleId, String startDate, String endDate, int itemsPerPage, int pageNum)
            throws MonIdpsException, ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = transFormat.parse(startDate);
        Date endDateTime = transFormat.parse(endDate);

        if (StringUtils.hasText(vehicleId) == true) {
            return new CanIdsDto.CanIdsLogListResponse();
        } else {
            List<CanIdsDetectionLog> canIdsDetectionLogList = canIdsDetectionLogRepository
                    .findDetectionLogOrderByDetectionTimeWithLimit(startDateTime, endDateTime, PageRequest.of(pageNum-1, itemsPerPage));

            List<CanIdsDto.CanIdsLog> canIdsLogList = canIdsDetectionLogList.stream()
                    .map(CanIdsDetectionLog::toCanIdsDto)
                    .collect(Collectors.toList());

            long totalCount = canIdsDetectionLogRepository.findDetectionLogCount(startDateTime, endDateTime);
            long totalPage = ((long)Math.ceil(totalCount / itemsPerPage)) + (((long)Math.ceil(totalCount % itemsPerPage)) != 0 ? 1 : 0);

            return CanIdsDto.CanIdsLogListResponse.builder()
                    .canIdsLogList(canIdsLogList)
                    .totalCount(totalCount)
                    .totalPage(totalPage)
                    .build();
        }
    }

    public void ChangeCanIdsLogLabel(CanIdsDto.LabelChangeReq req) {
        Optional<CanIdsDetectionLog> canIdsDetectionLog = canIdsDetectionLogRepository.findById(req.getId());

        canIdsDetectionLog.ifPresent(log -> {
            log.setLabel(req.getLabel());
            canIdsDetectionLogRepository.saveAndFlush(log);
        });
    }

    public byte[] exportCanIdsLog() throws Exception{
        List<CanIdsDetectionLog> canIdsDetectionLogList = canIdsDetectionLogRepository.findAll();
        List<CanIdsDto.CanIdsLog> canIdsLogList = canIdsDetectionLogList.stream()
                .map(CanIdsDetectionLog::toCanIdsDto)
                .collect(Collectors.toList());

        ArrayList<Map> canIdsLogMapList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (CanIdsDto.CanIdsLog canIdsLog : canIdsLogList) {
            canIdsLogMapList.add(objectMapper.convertValue(canIdsLog, Map.class));
        }
        return excelService.makeExcel("canIdsLog", canIdsLogMapList);
    }

}
