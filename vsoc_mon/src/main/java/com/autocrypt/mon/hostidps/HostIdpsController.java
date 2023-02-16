package com.autocrypt.mon.hostidps;

import com.autocrypt.mon.hostidps.dto.HostIdpsDto;
import com.autocrypt.mon.hostidps.service.HostIdpsService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hostidps")
public class HostIdpsController {

    private final HostIdpsService hostIdpsService;

    @ResponseBody
    @GetMapping("/overall")
    public HostIdpsDto.HostIdpsOverall GetHostIdpsOverall(@RequestParam(required = false) String region,
                                                          @RequestParam(required = false) String city,
                                                          @RequestParam String startDate,
                                                          @RequestParam String endDate) throws ParseException {
        // Request Host IDPS Overall Data ( totalDetections, averageDetections, detectedVehicles, hostIdpsTopFiveRules )
        return hostIdpsService.GetHostIdpsOverall(region, city, startDate, endDate);
    }

    @ResponseBody
    @GetMapping("/log")
    public HostIdpsDto.HostIdpsLogListResponse GetHostIdpsLogList(@RequestParam(required = false) String vehicleId,
                                                                  @RequestParam String type,
                                                                  @RequestParam String startDate,
                                                                  @RequestParam String endDate,
                                                                  @RequestParam Number itemsPerPage,
                                                                  @RequestParam Number pageNum) throws ParseException {
        // Request Table Data of Host IDPS Log, support Pagination
        return hostIdpsService.GetHostIdpsLogList(type, vehicleId, startDate, endDate, itemsPerPage.intValue(), pageNum.intValue());
    }

    @ResponseBody
    @PutMapping("/log")
    public ResponseEntity<?> ChangeHostIdpsLogLabel(@RequestBody HostIdpsDto.LabelChangeReq req) {
        // Request for changing log label of specific host idps log
        hostIdpsService.ChangeHostIdpsLogLabel(req);
        return ResponseEntity.ok("{\"result\" : \"ok\"}");
    }

    @ResponseBody
    @GetMapping("/export")
    public ResponseEntity<?> exportHostIdpsLog() throws Exception{
        // Export All Host IDPS Log Datas to Excel File
        byte[] excelByte = hostIdpsService.exportHostIdpsLog();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelByte);
    }
}
