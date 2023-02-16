package com.autocrypt.mon.policy.controller;

import com.autocrypt.mon.policy.dto.PolicyDto;
import com.autocrypt.mon.policy.service.PolicyService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;

    //Response to the policy of can ids.
    @ResponseBody
    @GetMapping("/canids")
    public PolicyDto.CanIdsPolicyList GetPolicyCanIds(@RequestBody Optional<String> name) {
        return this.policyService.GetPolicyCanIds(name);
    }

    //Response to the policy of host idps.
    @ResponseBody
    @GetMapping("/hostidps")
    public PolicyDto.HostIdpsPolicyList GetPolicyHostIdps(@RequestBody Optional<String> name) {
        return this.policyService.GetPolicyHostIdps(name);
    }

    //Response to the detailed information of the policy.
    @ResponseBody
    @GetMapping("/{policyType}/{policyId}")
    public PolicyDto.PolicyDetail GetPolicyDetail(@PathVariable("policyType") String policyType,
                                                  @PathVariable("policyId") String policyId) {
        return this.policyService.GetPolicyDetail(policyType, policyId);
    }

    //Reponse the log of the policy log.
    @ResponseBody
    @GetMapping("/{policyType}/{policyId}/{historyId}")
    public PolicyDto.PolicyLogList GetPolicyLog(@PathVariable("policyType") String policyType,
                                                @PathVariable("policyId") String policyId,
                                                @PathVariable("historyId") String historyId) {
        return this.policyService.GetPolicyLog(policyType, policyId, historyId);
    }

    //Take the policy list from db and respond.
    @ResponseBody
    @GetMapping("/{policyType}/{canId}/{version}/db")
    public PolicyDto.PolicyDbList GetPolicyDbList(@PathVariable("policyType") String policyType,
                                                  @PathVariable("canId") String canId,
                                                  @PathVariable("version") String version) {
        return this.policyService.GetPolicyDbList(policyType, canId, version);
    }

    //Take the bus data of the policy and respond.
    @ResponseBody
    @GetMapping("/{policyType}/{canId}/{version}/bus")
    public PolicyDto.PolicyBusData GetPolicyBusData(@PathVariable("policyType") String policyType,
                                                    @PathVariable("canId") String canId,
                                                    @PathVariable("version") String version) {
        return this.policyService.GetPolicyBusData(policyType, canId, version);
    }

    //Add a policy file.
    @ResponseBody
    @PostMapping("/add/{type}")
    public ResponseEntity<?> addPolicyFile(@RequestPart("policyFile") MultipartFile policyFile,
                                              @RequestPart("policyName") String policyName,
                                              @RequestPart("policyVersion")String policyVersion,
                                              @RequestPart("userName") String userName,
                                              @PathVariable("type") String type) throws IOException {

        PolicyDto.PolicyInfo policyInfo = PolicyDto.PolicyInfo.builder()
                .policyFile(policyFile)
                .policyName(policyName)
                .policyVersion(policyVersion)
                .userName(userName)
                .build();

        String resultMsg = "{\"result\" : \"ok\"}";

        if (type.equals("can") == true) {
            this.policyService.addCanPolicyFile(policyInfo);
        } else if (type.equals("host") == true){
            this.policyService.addHostPolicyFile(policyInfo);
        } else {
            resultMsg = "{\"result\" : \"error\"}";
        }

        return ResponseEntity.ok(resultMsg);
    }

    //The policy list is taken from db and responded.
    @ResponseBody
    @GetMapping("/{type}/info")
    public List<PolicyDto.PolicyListInfo> getPolicList(@PathVariable("type") String type) {
        return this.policyService.getPolicList(type);
    }
}
