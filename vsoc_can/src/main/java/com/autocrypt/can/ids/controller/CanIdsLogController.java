package com.autocrypt.can.ids.controller;

import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import com.autocrypt.can.ids.service.CanIdsLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/can/ids")
@RequiredArgsConstructor
public class CanIdsLogController {

    private final CanIdsLogService canIdsLogService;

    // Receive CAN IDS log from vSOC collector.
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public ResponseEntity<?> recvLogData(@RequestBody CanIdsLogDto.RequestDataDto data) throws ParseException {
        return canIdsLogService.receiveCanIdsLog(data);
    }

}
