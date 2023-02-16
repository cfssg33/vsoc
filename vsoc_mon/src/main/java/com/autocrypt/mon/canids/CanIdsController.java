package com.autocrypt.mon.canids;

import com.autocrypt.mon.canids.dto.CanIdsDto;
import com.autocrypt.mon.canids.service.CanIdsService;

import com.autocrypt.mon.common.exception.MonIdpsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/canids")
public class CanIdsController {

    private final CanIdsService canIdsService;

    // Returns overall information including last name, city, start time, and last time.
    @ResponseBody
    @GetMapping("/overall")
    public CanIdsDto.CanIdsOverall getCanIdsOverall(@RequestParam(required = false) String region,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam String startDate,
                                                    @RequestParam String endDate) throws ParseException {
        return canIdsService.GetCanIdsOverall(region, city, startDate, endDate);
    }

    // Returns CAN IDS log list including vehicleID, start time, and last time.
    @ResponseBody
    @GetMapping("/log")
    public CanIdsDto.CanIdsLogListResponse getCanIdsLogList(@RequestParam(required = false) String vehicleId,
                                                            @RequestParam String startDate,
                                                            @RequestParam String endDate,
                                                            @RequestParam Number itemsPerPage,
                                                            @RequestParam Number pageNum) throws MonIdpsException, ParseException {
        return canIdsService.GetCanIdsLogList(vehicleId, startDate, endDate, itemsPerPage.intValue(), pageNum.intValue());
    }

    // Update CAN IDS Log label information
    @ResponseBody
    @PutMapping("/log")
    public ResponseEntity<?> changeCanIdsLogLabel(@RequestBody CanIdsDto.LabelChangeReq req) {
        canIdsService.ChangeCanIdsLogLabel(req);
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }

    // Return CAN IDS all log to excel
    @ResponseBody
    @GetMapping("/export")
    public ResponseEntity<?> exportCanIdsLog() throws Exception{
        byte[] excelByte = canIdsService.exportCanIdsLog();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelByte);
    }

}
