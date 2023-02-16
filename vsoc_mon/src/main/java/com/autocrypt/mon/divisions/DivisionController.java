package com.autocrypt.mon.divisions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/divisions")
public class DivisionController {

    @GetMapping("/regionList")
    public String[] getRegionList() {
        return Divisions.FIRSTDIVISION.getArray();
    }

    @GetMapping("/cityList")
    public HashMap<String, String[]> getCityList() {
        HashMap<String, String[]> map = new HashMap<>();
        for (String region: Divisions.FIRSTDIVISION.getArray()) {
            map.put(region, Divisions.valueOf(region).getArray());
        }
        return map;
    }

}
