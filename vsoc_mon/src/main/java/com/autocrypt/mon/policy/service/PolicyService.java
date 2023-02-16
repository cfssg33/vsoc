package com.autocrypt.mon.policy.service;

import com.autocrypt.mon.policy.dto.PolicyDto;
import com.autocrypt.mon.policy.model.policyLog;
import com.autocrypt.mon.policy.repository.CanPolicyRepository;
import com.autocrypt.mon.policy.repository.HostPolicyRepository;
import com.autocrypt.mon.policy.repository.entity.CanPolicyInfo;
import com.autocrypt.mon.policy.repository.entity.HostPolicyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PolicyService {

    private final CanPolicyRepository canPolicyRepository;
    private final HostPolicyRepository hostPolicyRepository;

    private static final String CAN_POLICY_FILE_PATH = "/opt/autocrypt/vsoc/bin/policy/can";
    private static final String HOST_POLICY_FILE_PATH = "/opt/autocrypt/vsoc/bin/policy/host";

    public PolicyDto.CanIdsPolicyList GetPolicyCanIds(Optional<String> name) {
        PolicyDto.CanIdsPolicyList canIdsPolicyList = new PolicyDto.CanIdsPolicyList(new ArrayList<>());
        return canIdsPolicyList;
    }

    public PolicyDto.HostIdpsPolicyList GetPolicyHostIdps(Optional<String> name) {
        PolicyDto.HostIdpsPolicyList hostIdpsPolicyList = new PolicyDto.HostIdpsPolicyList(new ArrayList<>());
        return hostIdpsPolicyList;
    }

    public PolicyDto.PolicyDetail GetPolicyDetail(String policyType, String policyId) {
        String name = "test";
        PolicyDto.PolicyVersion policyVersion = new PolicyDto.PolicyVersion(new ArrayList<>());
        PolicyDto.PolicyHistory policyHistory = new PolicyDto.PolicyHistory(new ArrayList<>());
        PolicyDto.PolicyDetail policyDetail = new PolicyDto.PolicyDetail(name, policyVersion, policyHistory);

        return policyDetail;
    }

    public PolicyDto.PolicyLogList GetPolicyLog(String policyType, String policyId, String historyId) {
        PolicyDto.PolicyLogList policyLogList = new PolicyDto.PolicyLogList(new ArrayList<>());
        return policyLogList;
    }

    public PolicyDto.PolicyDbList GetPolicyDbList(String policyType, String canId, String version) {
        PolicyDto.PolicyDbList policyDbList = new PolicyDto.PolicyDbList(new ArrayList<>());
        return policyDbList;
    }

    public PolicyDto.PolicyBusData GetPolicyBusData(String policyType, String canId, String version) {
        String[] busNumbers = new String[5];
        String[] ruleTypes = new String[3];
        PolicyDto.PolicyBusList policyBusList = new PolicyDto.PolicyBusList(new ArrayList<>());

        PolicyDto.PolicyBusData policyBusData = new PolicyDto.PolicyBusData(busNumbers,
                                                                            ruleTypes,
                                                                            policyBusList);

        return policyBusData;
    }

    //TODO :file 무결성 검증 로직 추가해야 함
    @Transactional
    public void addCanPolicyFile (PolicyDto.PolicyInfo policyInfo) throws IOException {

        MultipartFile canPolicyFile  = policyInfo.getPolicyFile();

        Files.createDirectories(Paths.get(PolicyService.CAN_POLICY_FILE_PATH));
        canPolicyFile.transferTo(Paths.get(PolicyService.CAN_POLICY_FILE_PATH + "/" + canPolicyFile.getOriginalFilename()));

        CanPolicyInfo canPolicy = CanPolicyInfo.builder()
                .name(policyInfo.getPolicyName())
                .version(policyInfo.getPolicyVersion())
                .status("Published")
                .updatedBy(policyInfo.getUserName())
                .filePath(PolicyService.CAN_POLICY_FILE_PATH)
                .fileName(canPolicyFile.getOriginalFilename())
                .build();

        this.canPolicyRepository.save(canPolicy);

    }

    //TODO :file 무결성 검증 로직 추가해야 함
    @Transactional
    public void addHostPolicyFile (PolicyDto.PolicyInfo policyInfo) throws IOException {

        MultipartFile hostPolicyFile  = policyInfo.getPolicyFile();

        Files.createDirectories(Paths.get(PolicyService.HOST_POLICY_FILE_PATH));
        hostPolicyFile.transferTo(Paths.get(PolicyService.HOST_POLICY_FILE_PATH + "/" + hostPolicyFile.getOriginalFilename()));

        HostPolicyInfo hostPolicy = HostPolicyInfo.builder()
                .name(policyInfo.getPolicyName())
                .version(policyInfo.getPolicyVersion())
                .status("Published")
                .updatedBy(policyInfo.getUserName())
                .filePath(PolicyService.HOST_POLICY_FILE_PATH)
                .fileName(hostPolicyFile.getOriginalFilename())
                .build();

        this.hostPolicyRepository.save(hostPolicy);
    }

    public List<PolicyDto.PolicyListInfo> getPolicList (String type) {

        List<PolicyDto.PolicyListInfo> policyListInfo = new ArrayList<>();

        if (type.equals("can") == true) {

            List<policyLog> canPolicyInfoList = this.canPolicyRepository.findAllByGroupbyNameAndMaxVersion();
            canPolicyInfoList.stream().forEach((p) -> {
                policyListInfo.add(
                        PolicyDto.PolicyListInfo.builder()
                                .policyName(p.getName())
                                .policyVersion(p.getMaxVersion())
                                .status(p.getStatus())
                                .updatedBy(p.getUpdated_user())
                                .updateTime(p.getUpdate_time())
                                .build());
            });

        } else {
            List<policyLog> hostPolicyInfoList  = this.hostPolicyRepository.findAllByGroupbyNameAndMaxVersion();
            hostPolicyInfoList.stream().forEach((p) -> {
                policyListInfo.add(
                        PolicyDto.PolicyListInfo.builder()
                                .policyName(p.getName())
                                .policyVersion(p.getMaxVersion())
                                .status(p.getStatus())
                                .updatedBy(p.getUpdated_user())
                                .updateTime(p.getUpdate_time())
                                .build());
            });
        }

        return  policyListInfo;
    }
}
